package com.zoey.goods.api.controller;

import com.dashuai.commons.utils.Strings;
import com.dashuai.commons.utils.UniqueIdUtils;
import com.zoey.goods.api.support.CookieSupport;
import com.zoey.goods.model.body.UserLoginRequestBody;
import com.zoey.goods.model.body.UserRegisterRequestBody;
import com.zoey.goods.model.consts.GlobalConsts;
import com.zoey.goods.model.dto.UserInfoDTO;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.result.ApiResults;
import com.zoey.goods.service.IBuyerUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/buyer")
public class UserApis extends CookieSupport {

    @Autowired
    IBuyerUserInfoService buyerUserInfoService;

    @PostMapping("/register")
    public ApiResults register(@Validated @RequestBody UserRegisterRequestBody requestBody) {
        String requestId = UniqueIdUtils.getUUIdStr();
        boolean result = buyerUserInfoService.registerUser(requestBody, requestId);
        if (result) {
            return ApiResults.prepare().success("注册成功,清前往登录!");
        }
        return ApiResults.prepare().error(null, GlobalConsts.FAILURE_CODE, "注册失败,请检查信息是否有误!");
    }

    @PostMapping("/login")
    public ApiResults userLogin(@Validated @RequestBody UserLoginRequestBody userInfoRequestBody, HttpServletResponse response) {
        String requestId = UniqueIdUtils.getUUIdStr();
        UserInfoDTO userInfo = buyerUserInfoService.login(userInfoRequestBody, requestId);
        String token = buyerUserInfoService.createToken(userInfo, requestId);
        //设置cookie
        Cookie cookie = prepareCookie(token);
        response.addCookie(cookie);
        return ApiResults.prepare().success(userInfo.getUserNick());
    }

    @PostMapping("/logout")
    public ApiResults userLogout(@CookieValue(name = GlobalConsts.LOGIN_COOKIE_KEY) String token, HttpServletResponse response) {
        if (Strings.isNullOrEmpty(token)) {
            return ApiResults.prepare().error(null, ErrorCodeEnum.LOGIN900001.code(), "授权信息为空!");
        }
        String requestId = UniqueIdUtils.getUUIdStr();
        buyerUserInfoService.logout(token, requestId);
        response.addCookie(removeCookie(token));
        return ApiResults.prepare().success("注销成功");
    }

    @PostMapping("/getPublicKey")
    public ApiResults getPublicKey(String userName) {
        String requestId = UniqueIdUtils.getUUIdStr();
        return ApiResults.prepare().success(buyerUserInfoService.getPublicKey(userName, requestId));
    }
}
