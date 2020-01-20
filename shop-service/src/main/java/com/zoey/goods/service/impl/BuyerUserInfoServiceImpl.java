package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoey.goods.dao.order.entity.UserInfo;
import com.zoey.goods.dao.order.mapper.UserInfoMapper;
import com.zoey.goods.service.IBuyerUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey
 * @since 2019-12-25
 */
@Service
public class BuyerUserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IBuyerUserInfoService {

}
