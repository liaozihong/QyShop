package com.zoey.goods.api.interceptor;

import com.dashuai.commons.utils.Strings;
import com.dashuai.commons.utils.UniqueIdUtils;
import com.zoey.goods.api.support.CookieSupport;
import com.zoey.goods.model.consts.GlobalConsts;
import com.zoey.goods.model.dto.UserInfoDTO;
import com.zoey.goods.service.IBuyerUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created in 2020.03.09
 * <p>
 * 登录拦截器
 *
 * @author ZH ·L
 */
@Component
public class LoginInterceptor extends CookieSupport implements HandlerInterceptor {

    /**
     * The Buyer user info service.
     */
    @Autowired
    IBuyerUserInfoService buyerUserInfoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UniqueIdUtils.getUUIdStr();
        String paramToken = request.getParameter(GlobalConsts.LOGIN_COOKIE_KEY);
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName()
                .equals(GlobalConsts.LOGIN_COOKIE_KEY)).findAny();
        if (Strings.isNullOrEmpty(paramToken) && optionalCookie.isPresent()) {
            return false;
        }
        String token = paramToken == null ? optionalCookie.get().getValue() : paramToken;
        UserInfoDTO userInfoDTO = buyerUserInfoService.validationLogin(token, requestId);
        //cookie续费
        if (userInfoDTO != null) {
            prepareCookie(token);
        }
        return userInfoDTO != null;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
