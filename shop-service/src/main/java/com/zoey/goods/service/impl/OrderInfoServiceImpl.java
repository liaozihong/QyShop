package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashuai.commons.redis.RedisPool;
import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zoey.goods.comment.utils.OrderNoCreator;
import com.zoey.goods.dao.order.entity.OrderInfo;
import com.zoey.goods.dao.order.mapper.OrderInfoMapper;
import com.zoey.goods.model.GoodsSku;
import com.zoey.goods.model.Skus;
import com.zoey.goods.model.consts.MqGroupNameConst;
import com.zoey.goods.model.consts.MqTopicConst;
import com.zoey.goods.model.consts.RedisConsts;
import com.zoey.goods.model.dto.BuyerGoodsInfoDTO;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.enums.MqTagEnum;
import com.zoey.goods.model.exception.OrderException;
import com.zoey.goods.model.exception.StoreException;
import com.zoey.goods.model.mq.body.OrderCreateMqBody;
import com.zoey.goods.model.result.GoodsResult;
import com.zoey.goods.service.IGoodsDetailsService;
import com.zoey.goods.service.IGoodsInfoService;
import com.zoey.goods.service.IOrderInfoService;
import com.zoey.goods.service.mq.RocketMqProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey ·L
 * @since 2019 -12-25
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

    private RedisPool redisPool;
    private IGoodsInfoService goodsInfoService;
    private IGoodsDetailsService goodsDetailsService;

    public OrderInfoServiceImpl(RedisPool redisPool, IGoodsInfoService goodsInfoService, IGoodsDetailsService goodsDetailsService) {
        this.redisPool = redisPool;
        this.goodsInfoService = goodsInfoService;
        this.goodsDetailsService = goodsDetailsService;
    }

    private final Logger logger = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Override
    @Transactional(propagation = Propagation.NESTED, isolation = Isolation.DEFAULT,
            rollbackFor = Exception.class)
    public String createSeckillOrder(Integer userId, Integer gid, Long skuId,
                                     Integer buyerCount, String requestId) {
        //减库存操作
        Long result = operationCacheStore(gid, skuId, -buyerCount, requestId);
        if (result == 1) {
            String orderNo = OrderNoCreator.getOrderNo(String.valueOf(userId));
            //预下单，发送Mq消息
            OrderCreateMqBody orderCreateMqBody = OrderCreateMqBody.builder().buyerCount(buyerCount).gid(gid).userId(userId)
                    .orderNo(orderNo).skuId(skuId).requestId(requestId).build();
            preOrder(orderCreateMqBody, requestId);
            return orderNo;
        } else if (result == 0) {
            throw new StoreException(ErrorCodeEnum.STORE200001, requestId);
        } else {
            //TODO 没缓存，可能是过了抢购期或Redis挂了,
            throw new OrderException(ErrorCodeEnum.STORE200003, requestId);
        }
    }

    /**
     * 预下单
     *
     * @param orderCreateMqBody the order create mq body
     * @param requestId         the request id
     */
    public void preOrder(OrderCreateMqBody orderCreateMqBody, String requestId) {
        RocketMqProducer.sendSimpleMessage(JasksonUtils.toJson(orderCreateMqBody), MqTopicConst.ORDER_TOPIC,
                MqTagEnum.CreateOrder.getTag(), requestId, MqGroupNameConst.ORDER_CREATE_PRODUCER_GROUP, null);
    }

    private OrderInfo fillOrderInfo(String orderNo, GoodsSku goodsSku, Integer userId, Integer gid, Long skuId,
                                    Integer buyerCount, String requestId) {
        //propertiesName 结构为： 1627207:28327:酒红色;20509:28318:大码XXL
        String[] props = goodsSku.getPropertiesName().split(";");
        BigDecimal totalPrice = BigDecimal.valueOf(goodsSku.getPrice2()).multiply(BigDecimal.valueOf(buyerCount));
        BuyerGoodsInfoDTO buyerGoodsInfoDTO = BuyerGoodsInfoDTO.builder().colorName(props[0].split(":")[2])
                .sizeName(props[1].split(":")[2]).skuId(skuId).gid(gid).buyerCount(buyerCount)
                .price(BigDecimal.valueOf(goodsSku.getPrice2()))
                .totlePrice(totalPrice)
                .build();
        return OrderInfo.builder().orderNo(orderNo).userId(userId)
                .totalMoney(totalPrice).goodsList(JasksonUtils.toJson(
                        Collections.singleton(buyerGoodsInfoDTO))).build();
    }

    /**
     * 获取SKU信息
     *
     * @param gid
     * @param requestId
     * @return
     */
    private Skus getSkuInfo(Integer gid, String requestId) {
        try {
            GoodsResult goodsResult = goodsInfoService.getFullGoodsInfo(gid, requestId);
            return JasksonUtils.json2GenericObject(goodsResult.getSkuJson(), new TypeReference<Skus>() {
            });
        } catch (Exception e) {
            logger.error("序列化SkuJson信息失败！msg:{}", e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean handlerOrderGenerate(String orderBody, String requestId) {
        OrderCreateMqBody orderCreateMqBody = JasksonUtils.jsonToObject(orderBody, OrderCreateMqBody.class);
        try {
            Skus skus = getSkuInfo(orderCreateMqBody.getGid(), requestId);
            GoodsSku goodsSku = null;
            if (skus != null && skus.getSku() != null && !skus.getSku().isEmpty()) {
                goodsSku = skus.getSku().stream().filter(sku -> sku.getSkuId() == orderCreateMqBody.getSkuId()).findAny().get();
            } else {
                throw new OrderException(ErrorCodeEnum.GOODS300001, requestId);
            }
            OrderInfo orderInfo = fillOrderInfo(orderCreateMqBody.getOrderNo(), goodsSku,
                    orderCreateMqBody.getUserId(), orderCreateMqBody.getGid(), orderCreateMqBody.getSkuId(),
                    orderCreateMqBody.getBuyerCount(), requestId);
            if (orderInfo != null) {
                boolean isSuccess = this.save(orderInfo);
                logger.info("订单入库{},orderNo:{}", isSuccess ? "成功" : "失败", orderInfo.getOrderNo());
                if (isSuccess) {
                    return true;
                }
            }
            throw new OrderException(ErrorCodeEnum.ORDER100001, requestId);
        } catch (Exception e) {
            //出现异常，回滚，加回库存
            operationCacheStore(orderCreateMqBody.getGid(), orderCreateMqBody.getSkuId(),
                    orderCreateMqBody.getBuyerCount(), requestId);
            throw e;
        }
    }

    /**
     * 操作缓存中的商品库存
     *
     * @param gid
     * @param skuId
     * @param buyerCount
     * @param requestId
     * @return
     */
    private Long operationCacheStore(Integer gid, Long skuId, Integer buyerCount, String requestId) {
        try {
            //减库存
            return redisPool.evalLua(RedisConsts.SUB_STORE_SCRIPT,
                    1, RedisConsts.GOODS_STORE_PREFIX + gid + ":" + skuId,
                    String.valueOf(buyerCount));
        } catch (Exception e) {
            logger.error("减库存REDIS LUA ERR,原因:{},requestId:{}", e.getMessage(), requestId);
        }
        return -1L;
    }
}
