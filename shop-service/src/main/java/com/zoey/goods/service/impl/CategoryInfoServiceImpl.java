package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoey.goods.dao.goods.entity.CategoryInfo;
import com.zoey.goods.dao.goods.mapper.CategoryInfoMapper;
import com.zoey.goods.service.ICategoryInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey
 * @since 2019-12-16
 */
@Service
public class CategoryInfoServiceImpl extends ServiceImpl<CategoryInfoMapper, CategoryInfo> implements ICategoryInfoService {
    @Override
    public CategoryInfo getCategoryInfo(Long cid) {
        return getOne(Wrappers.<CategoryInfo>lambdaQuery().select(CategoryInfo::getCid,
                CategoryInfo::getName, CategoryInfo::getCatePath).eq(CategoryInfo::getCid, cid));
    }
}
