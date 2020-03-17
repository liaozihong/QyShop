package com.zoey.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoey.goods.dao.user.entity.QyUserInfo;
import com.zoey.goods.model.body.UserLoginRequestBody;
import com.zoey.goods.model.body.UserRegisterRequestBody;
import com.zoey.goods.model.dto.UserInfoDTO;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zoey ·L
 * @since 2019 -12-25
 */
public interface IBuyerUserInfoService extends IService<QyUserInfo> {
    /**
     * Register user boolean.
     *
     * @param requestBody the request body
     * @param requestId   the request id
     * @return the boolean
     */
    boolean registerUser(UserRegisterRequestBody requestBody, String requestId);

    /**
     * Login user info.
     *
     * @param userInfoRequestBody the user info request body
     * @param requestId           the request id
     * @return the user info
     */
    UserInfoDTO login(UserLoginRequestBody userInfoRequestBody, String requestId);

    /**
     * Create token string.
     *
     * @param userInfo  the user info
     * @param requestId the request id
     * @return the string
     */
    String createToken(UserInfoDTO userInfo, String requestId);


    /**
     * Validation login qy user info.
     *
     * @param token     the token
     * @param requestId the request id
     * @return the qy user info
     */
    UserInfoDTO validationLogin(String token, String requestId);

    /**
     * Gets public key.
     *
     * @param userName  the user name
     * @param requestId the request id
     * @return the public key
     */
    Map<String, String> getPublicKey(String userName, String requestId);

    /**
     * Logout boolean.
     *
     * @param token     the token
     * @param requestId the request id
     * @return the boolean
     */
    void logout(String token, String requestId);
}
