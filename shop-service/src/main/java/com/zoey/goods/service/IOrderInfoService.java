package com.zoey.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.order.entity.OrderInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zoey ·L
 * @since 2019 -12-25
 */
public interface IOrderInfoService extends IService<OrderInfo> {
    /**
     * Create seckill order order create status enum.
     *
     * @param userId     the user id
     * @param gid        the gid
     * @param skuId      the sku id
     * @param buyerCount the buyer count
     * @param requestId  the request id
     * @return the order create status enum
     */
    String createSeckillOrder(Integer userId, Integer gid, Long skuId, Integer buyerCount, String requestId);


    /**
     * Order generate boolean.
     *
     * @param orderBody the order body
     * @param requestId the request id
     * @return the boolean
     */
    Boolean handlerOrderGenerate(String orderBody, String requestId);
}
