package com.zoey.goods.consumer.config;

import com.dashuai.commons.redis.RedisPool;
import com.zoey.goods.service.ICategoryInfoService;
import com.zoey.goods.service.IGoodsDetailsService;
import com.zoey.goods.service.IGoodsInfoService;
import com.zoey.goods.service.IOrderInfoService;
import com.zoey.goods.service.impl.CategoryInfoServiceImpl;
import com.zoey.goods.service.impl.GoodsDetailsServiceImpl;
import com.zoey.goods.service.impl.GoodsInfoServiceImpl;
import com.zoey.goods.service.impl.OrderInfoServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {
    @Bean
    public IGoodsInfoService goodsInfoService() {
        return new GoodsInfoServiceImpl();
    }

    @Bean
    public IGoodsDetailsService goodsDetailsService() {
        return new GoodsDetailsServiceImpl();
    }

    @Bean
    public IOrderInfoService orderInfoService(RedisPool redisPool) {
        return new OrderInfoServiceImpl(redisPool, goodsInfoService(), goodsDetailsService());
    }

    @Bean
    public ICategoryInfoService categoryInfoService() {
        return new CategoryInfoServiceImpl();
    }
}
