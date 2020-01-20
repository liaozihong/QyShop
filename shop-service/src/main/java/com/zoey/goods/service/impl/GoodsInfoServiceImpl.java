package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashuai.commons.api.RedisCacheApi;
import com.dashuai.commons.redis.RedisPool;
import com.dashuai.commons.redis.support.CacheSetFunction;
import com.dashuai.commons.redis.support.ServiceCacheSetSupport;
import com.dashuai.commons.utils.BeanMapperUtils;
import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zoey.goods.dao.goods.entity.CategoryInfo;
import com.zoey.goods.dao.goods.entity.GoodsDetails;
import com.zoey.goods.dao.goods.entity.GoodsInfo;
import com.zoey.goods.dao.goods.mapper.GoodsInfoMapper;
import com.zoey.goods.model.Skus;
import com.zoey.goods.model.consts.RedisConsts;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.exception.StoreException;
import com.zoey.goods.model.result.GoodsResult;
import com.zoey.goods.service.ICategoryInfoService;
import com.zoey.goods.service.IGoodsDetailsService;
import com.zoey.goods.service.IGoodsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey ·L
 * @since 2019 -12-16
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo>
        implements IGoodsInfoService, ServiceCacheSetSupport {


    @Autowired
    private IGoodsDetailsService goodsDetailsService;
    @Autowired
    private ICategoryInfoService categoryInfoService;
    @Autowired
    private RedisCacheApi redisCache;
    @Autowired
    private RedisPool redisPool;
    @Value("${goods.cache.timeout:720}")
    private Integer goodsCacheTimeout;

    private final Logger logger = LoggerFactory.getLogger(GoodsInfoServiceImpl.class);


    @Override
    public IPage<GoodsInfo> getGoodsList(Integer pageNo, Integer pageSize, String requestId) {
        Page<GoodsInfo> page = new Page<>(pageNo, pageSize);
        return this.page(page, new QueryWrapper<GoodsInfo>()
                .select("gid", "goods_title", "goods_no", "goods_price", "goods_discount_price", "goods_pic_url", "create_at"));
    }

    @Override
    public GoodsInfo getGoodsInfo(Integer gid, String requestId) {
        return getOne(Wrappers.<GoodsInfo>lambdaQuery().eq(GoodsInfo::getGid, gid));
    }

    @Override
    public GoodsResult getFullGoodsInfo(Integer gid, String requestId) {
        String goodsKey = RedisConsts.SECKILL_GOODS_PREFIX + gid;
        return invokeCacheSetService(new CacheSetFunction<GoodsResult>() {
            @Override
            public GoodsResult execute() {
                GoodsInfo goodsInfo = getGoodsInfo(gid, requestId);
                GoodsResult goodsResult = BeanMapperUtils.mapperFast(goodsInfo, GoodsResult.class);
                GoodsDetails goodsDetails = goodsDetailsService.getGoodsDetails(gid, requestId);
                goodsResult.setSkuJson(goodsDetails.getSkuJson());
                goodsResult.setInputPids(goodsDetails.getInputPids());
                goodsResult.setInputStr(goodsDetails.getInputStr());
                goodsResult.setPropsNames(goodsDetails.getPropsNames());
                goodsResult.setGoodsDesc(goodsDetails.getGoodsDesc());
                CategoryInfo categoryInfo = categoryInfoService.getCategoryInfo(goodsInfo.getGoodsCid());
                goodsResult.setGoodsCname(categoryInfo.getName());
                goodsResult.setCatePath(categoryInfo.getCatePath());
                //将库存信息设置进cache，后面的减库存操作全去redis上操作。
                settingStoreCache(goodsInfo, goodsResult.getSkuJson(), requestId);
                return goodsResult;
            }

            @Override
            public TypeReference<GoodsResult> getResultType() {
                return new TypeReference<GoodsResult>() {
                };
            }
        }, goodsKey, goodsCacheTimeout, false, requestId);
    }

    /**
     * 设置库存缓存
     */
    private void settingStoreCache(GoodsInfo goodsInfo, String skuJson, String requestId) {
        long seckillEndTime = Timestamp.valueOf(goodsInfo.getSeckillEndTime()).getTime();
        long now = Timestamp.valueOf(LocalDateTime.now()).getTime();
        int seckillStoreCacheTime = (int) ((seckillEndTime - now) / 1000);
        //默认抢购从缓存中操作库存，库存不存在，则报错
        if (seckillStoreCacheTime < 0) {
            logger.info("已过抢购时间，不设置库存缓存!");
            return;
        }
        Skus skus = JasksonUtils.json2GenericObject(skuJson, new TypeReference<Skus>() {
        });
        try {
            if (skus != null && skus.getSku() != null && !skus.getSku().isEmpty()) {
                skus.getSku().forEach(sku -> {
                    String key = RedisConsts.GOODS_STORE_PREFIX + goodsInfo.getGid() + ":" + sku.getSkuId();
                    if (!redisPool.isExistKey(key)) {
                        redisCache.setString(key, String.valueOf(sku.getNum()), seckillStoreCacheTime);
                    }
                });
            }
        } catch (Exception e) {
            throw new StoreException(ErrorCodeEnum.CACHE8000001, requestId);
        }
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public RedisCacheApi getRedisApi() {
        return redisCache;
    }
}
