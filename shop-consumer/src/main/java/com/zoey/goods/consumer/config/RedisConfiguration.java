package com.zoey.goods.consumer.config;

import com.dashuai.commons.api.RedisCacheApi;
import com.dashuai.commons.api.RedisLockApi;
import com.dashuai.commons.redis.DefaultRedisCacheApi;
import com.dashuai.commons.redis.DefaultRedisLockApi;
import com.dashuai.commons.redis.RedisPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created in 2020.01.16
 *
 * @author ZH ·L
 */
@Import(value = com.dashuai.commons.redis.RedisConfiguration.class)
@Configuration
public class RedisConfiguration {

    /**
     * Redis lock redis lock api.
     *
     * @param redisPool the redis pool
     * @return the redis lock api
     */
    @Bean
    public RedisLockApi redisLock(RedisPool redisPool) {
        return new DefaultRedisLockApi(redisPool);
    }

    /**
     * Redis cache redis cache api.
     *
     * @param redisPool the redis pool
     * @return the redis cache api
     */
    @Bean
    public RedisCacheApi redisCache(RedisPool redisPool) {
        return new DefaultRedisCacheApi(redisPool);
    }
}
