package com.zoey.goods.api.config;

import com.dashuai.commons.api.HttpClientApi;
import com.dashuai.commons.redis.RedisPool;
import com.dashuai.commons.utils.JasksonUtils;
import com.dashuai.learning.client.HttpClientPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zoey.goods.comment.support.SpringContextHolder;
import com.zoey.goods.service.*;
import com.zoey.goods.service.impl.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created in 2019.12.16
 *
 * @author Zoey
 */
@Configuration
public class SpringConfiguration {
    /**
     * 更改springboot 默认的json解析
     *
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper jacksonObjectMapper() {
        return JasksonUtils.getObjectMapper();
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    public HttpClientApi httpClient() {
        return new HttpClientPool();
    }

    /**
     * Goods info service goods info service.
     *
     * @return the goods info service
     */
    @Bean
    public IGoodsInfoService goodsInfoService() {
        return new GoodsInfoServiceImpl();
    }

    @Bean
    public IGoodsDetailsService goodsDetailsService() {
        return new GoodsDetailsServiceImpl();
    }

    @Bean
    public ICategoryInfoService categoryInfoService() {
        return new CategoryInfoServiceImpl();
    }

    @Bean
    public IOrderInfoService orderInfoService(RedisPool redisPool) {
        return new OrderInfoServiceImpl(redisPool, goodsInfoService(), goodsDetailsService());
    }

    @Bean
    public IBuyerUserInfoService buyerUserInfoService() {
        return new BuyerUserInfoServiceImpl();
    }

    @Bean
    public IAreasService areasService() {
        return new AreasServiceImpl();
    }

}
