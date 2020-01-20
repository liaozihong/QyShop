package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zoey.goods.dao.goods.entity.GoodsDetails;
import com.zoey.goods.dao.goods.mapper.GoodsDetailsMapper;
import com.zoey.goods.model.GoodsSku;
import com.zoey.goods.model.Skus;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.exception.StoreException;
import com.zoey.goods.service.IGoodsDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey
 * @since 2019-12-16
 */
@Service
public class GoodsDetailsServiceImpl extends ServiceImpl<GoodsDetailsMapper, GoodsDetails> implements IGoodsDetailsService {

    private final Logger logger = LoggerFactory.getLogger(GoodsDetailsServiceImpl.class);

    @Override
    public GoodsDetails getGoodsDetails(Integer gid, String requestId) {
        return this.getOne(new QueryWrapper<GoodsDetails>().lambda().eq(GoodsDetails::getGid, gid));
    }

    @Override
    public boolean subtractGoodsStore(Integer gid, Long skuId, Integer count, String requestId) {
        GoodsDetails goodsDetails = this.getOne(Wrappers.<GoodsDetails>lambdaQuery().eq(GoodsDetails::getGid, gid));
        Skus skus = JasksonUtils.json2GenericObject(goodsDetails.getSkuJson(), new TypeReference<Skus>() {
        });
        if (skus != null && skus.getSku() != null && !skus.getSku().isEmpty()) {
            Optional<GoodsSku> optionalGoodsSku = skus.getSku().stream().filter(sku -> sku.getSkuId() == skuId).findFirst();
            if (!optionalGoodsSku.isPresent()) {
                throw new StoreException(ErrorCodeEnum.STORE200001, requestId);
            }
            if (optionalGoodsSku.get().getNum() >= count) {
                optionalGoodsSku.get().setNum(optionalGoodsSku.get().getNum() - count);
                boolean isSuccess = this.update().set("version", goodsDetails.getVersion())
                        .set("sku_json", JasksonUtils.toJson(skus)).update();
                if (isSuccess) {
                    return true;
                }
                throw new StoreException(ErrorCodeEnum.STORE200002, requestId);
            }
            throw new StoreException(ErrorCodeEnum.STORE200001, requestId);
        }
        throw new StoreException(ErrorCodeEnum.STORE200003, requestId);
    }
}
