package com.zoey.goods.model.consts;

/**
 * Created in 2019.12.25
 *
 * @author Zoey
 */
public class RedisConsts {

    /**
     * 秒杀商品信息缓存前缀
     */
    public final static String SECKILL_GOODS_PREFIX = "seckill:goods:";
    /**
     * 商品库存缓存
     */
    public final static String GOODS_STORE_PREFIX = "goods:store:";
    /**
     * 减库存LUA脚本.
     */
    public final static String SUB_STORE_SCRIPT = "local is_exists = redis.call(\"exists\", KEYS[1]) \n" +
            "if is_exists == 1 then \n" +
            "local stockAftChange=redis.call(\"INCRBY\",KEYS[1],ARGV[1])\n" +
            "if (stockAftChange < 0 ) then\n" +
            "redis.call(\"DECRBY\", KEYS[1], ARGV[1])\n" +
            "return 0\n" +
            "else\n" +
            "return 1\n" +
            "end\n" +
            "else\n" +
            "return -1\n" +
            "end ";
}
