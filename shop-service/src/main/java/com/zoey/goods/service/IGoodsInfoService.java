package com.zoey.goods.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.goods.entity.GoodsInfo;
import com.zoey.goods.model.result.GoodsResult;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zoey
 * @since 2019 -12-16
 */
public interface IGoodsInfoService extends IService<GoodsInfo> {

    /**
     * Gets goods list.
     *
     * @param pageNo    the page no
     * @param pageSize  the page size
     * @param requestId the request id
     * @return the goods list
     */
    IPage<GoodsInfo> getGoodsList(Integer pageNo, Integer pageSize, String requestId);

    /**
     * Gets goods info.
     *
     * @param gid       the gid
     * @param requestId the request id
     * @return the goods info
     */
    GoodsInfo getGoodsInfo(Integer gid, String requestId);

    /**
     * 获取完整商品信息
     *
     * @param gid
     * @param requestId
     * @return
     */
    GoodsResult getFullGoodsInfo(Integer gid, String requestId);

}
