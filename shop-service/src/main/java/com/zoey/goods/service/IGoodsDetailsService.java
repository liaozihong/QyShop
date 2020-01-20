package com.zoey.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.goods.entity.GoodsDetails;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zoey
 * @since 2019 -12-16
 */
public interface IGoodsDetailsService extends IService<GoodsDetails> {

    /**
     * Gets goods details.
     *
     * @param gid       the gid
     * @param requestId the request id
     * @return the goods details
     */
    GoodsDetails getGoodsDetails(Integer gid, String requestId);

    /**
     * 减库存方法
     *
     * @param gid
     * @param skuId
     * @param count
     * @param requestId
     * @return
     */
    boolean subtractGoodsStore(Integer gid, Long skuId, Integer count, String requestId);

}
