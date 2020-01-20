package com.zoey.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.areas.entity.QyAreas;

import java.io.IOException;

/**
 * <p>
 * 城市地区表 服务类
 * </p>
 *
 * @author zoey
 * @since 2020-01-19
 */
public interface IAreasService extends IService<QyAreas> {
    /**
     * @param requestId
     * @throws IOException
     */
    void getAreasData(String requestId) throws IOException;
}
