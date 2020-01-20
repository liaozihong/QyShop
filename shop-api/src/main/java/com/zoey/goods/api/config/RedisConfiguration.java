package com.zoey.goods.api.config;

import com.dashuai.commons.api.RedisCacheApi;
import com.dashuai.commons.api.RedisLockApi;
import com.dashuai.commons.redis.DefaultRedisCacheApi;
import com.dashuai.commons.redis.DefaultRedisLockApi;
import com.dashuai.commons.redis.RedisPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(com.dashuai.commons.redis.RedisConfiguration.class)
@Configuration
public class RedisConfiguration {

    @Bean
    public RedisLockApi redisLock(RedisPool redisPool) {
        return new DefaultRedisLockApi(redisPool);
    }

    @Bean
    public RedisCacheApi redisCache(RedisPool redisPool) {
        return new DefaultRedisCacheApi(redisPool);
    }
}
