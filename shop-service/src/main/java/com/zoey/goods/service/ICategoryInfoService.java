package com.zoey.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.goods.entity.CategoryInfo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zoey
 * @since 2019-12-16
 */
public interface ICategoryInfoService extends IService<CategoryInfo> {
    CategoryInfo getCategoryInfo(Long cid);

}
