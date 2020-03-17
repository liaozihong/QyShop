package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashuai.commons.api.RedisCacheApi;
import com.dashuai.commons.redis.RedisPool;
import com.dashuai.commons.utils.BeanMapperUtils;
import com.dashuai.commons.utils.EncryptionUtils;
import com.dashuai.commons.utils.HexUtils;
import com.dashuai.commons.utils.JasksonUtils;
import com.zoey.goods.comment.utils.RSAUtils;
import com.zoey.goods.dao.user.entity.QyUserInfo;
import com.zoey.goods.dao.user.mapper.QyUserInfoMapper;
import com.zoey.goods.model.body.UserLoginRequestBody;
import com.zoey.goods.model.body.UserRegisterRequestBody;
import com.zoey.goods.model.consts.RedisConsts;
import com.zoey.goods.model.dto.UserInfoDTO;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.exception.LoginFailureException;
import com.zoey.goods.model.exception.RegisterFailureException;
import com.zoey.goods.service.IBuyerUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zoey
 * @since 2019-12-25
 */
@Service
public class BuyerUserInfoServiceImpl extends ServiceImpl<QyUserInfoMapper, QyUserInfo> implements IBuyerUserInfoService {

    @Autowired
    QyUserInfoMapper userInfoMapper;

    @Autowired
    RedisCacheApi redisCacheApi;

    @Value("${login.token.timeout:600}")
    Integer loginTokenTimeout;
    @Autowired
    RedisPool redisPool;

    @Override
    public boolean registerUser(UserRegisterRequestBody requestBody, String requestId) {
        QyUserInfo qyUserInfo = BeanMapperUtils.mapperFast(requestBody, QyUserInfo.class);
        Integer emailCount = userInfoMapper.selectCount(Wrappers.<QyUserInfo>lambdaQuery().eq(QyUserInfo::getEmail, qyUserInfo.getEmail()));
        if (emailCount > 0) {
            throw new RegisterFailureException(ErrorCodeEnum.REGISTER800002, requestId);
        }
        Integer userCount = userInfoMapper.selectCount(Wrappers.<QyUserInfo>lambdaQuery().eq(QyUserInfo::getUserNick, qyUserInfo.getUserNick()));
        if (userCount > 0) {
            throw new RegisterFailureException(ErrorCodeEnum.REGISTER800001, requestId);
        }
        String password = RSAUtils.decryptBase64(requestBody.getUserPassword());
        qyUserInfo.setUserPassword(password);
        int isSuccess = userInfoMapper.insert(qyUserInfo);
        if (isSuccess > 0) {
            return true;
        }
        return false;
    }

    @Override
    public UserInfoDTO login(UserLoginRequestBody userInfoRequestBody, String requestId) {
        QyUserInfo userInfo = userInfoMapper.selectOne(Wrappers.<QyUserInfo>lambdaQuery().eq(QyUserInfo::getUserNick, userInfoRequestBody.getUserNick()));
        if (userInfo == null) {
            throw new LoginFailureException(ErrorCodeEnum.LOGIN900002, requestId);
        }
        String originPass = RSAUtils.decryptBase64(userInfoRequestBody.getUserPassword());
        if (userInfo.getUserPassword().equals(originPass)) {
            return BeanMapperUtils.mapperFast(userInfo, UserInfoDTO.class);
        } else {
            throw new LoginFailureException(ErrorCodeEnum.LOGIN900003, requestId);
        }
    }

    @Override
    public String createToken(UserInfoDTO userInfo, String requestId) {
        String token = HexUtils.parseByte2UppercaseHexString(EncryptionUtils.encryptMD5(
                userInfo.getUserId() + userInfo.getUserNick() + requestId));
        boolean isSuccess = redisCacheApi.setString(RedisConsts.USER_TOKEN_PREFIX + token,
                JasksonUtils.toJson(userInfo), loginTokenTimeout);
        if (isSuccess) {
            return token;
        }
        throw new LoginFailureException(ErrorCodeEnum.LOGIN900005, requestId);
    }

    @Override
    public UserInfoDTO validationLogin(String token, String requestId) {
        String json = redisCacheApi.getString(RedisConsts.USER_TOKEN_PREFIX + token);
        if (json == null) {
            throw new LoginFailureException(ErrorCodeEnum.LOGIN900001, requestId);
        }
        UserInfoDTO userInfo = JasksonUtils.jsonToObject(json, UserInfoDTO.class);
        //redis 用户信息过期时间更新
        createToken(userInfo, requestId);
        return userInfo;
    }

    @Override
    public Map<String, String> getPublicKey(String userName, String requestId) {
        Map<String, String> result = new HashMap<>(4);
        result.put("publicKey", RSAUtils.generateBase64PublicKey());
        result.put("key", HexUtils.parseByte2UppercaseHexString(EncryptionUtils.encryptMD5(userName + requestId)));
        return result;
    }

    @Override
    public void logout(String token, String requestId) {
        if (!redisCacheApi.del(RedisConsts.USER_TOKEN_PREFIX + token)) {
            throw new LoginFailureException(ErrorCodeEnum.LOGIN900006, requestId);
        }
    }
}
