package com.zoey.goods.api.support;

import com.zoey.goods.model.consts.GlobalConsts;

import javax.servlet.http.Cookie;

public class CookieSupport {
    /**
     * set cookie
     *
     * @param token token key
     * @return cookie
     */
    protected Cookie prepareCookie(String token) {
        Cookie tokenCookie = new Cookie(GlobalConsts.LOGIN_COOKIE_KEY, token);
        tokenCookie.setDomain(GlobalConsts.DOMAIN);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(GlobalConsts.LOGIN_COOKIE_TIMEOUT);
        return tokenCookie;
    }

    protected Cookie removeCookie(String token) {
        Cookie tokenCookie = new Cookie(GlobalConsts.LOGIN_COOKIE_KEY, token);
        tokenCookie.setDomain(GlobalConsts.DOMAIN);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        return tokenCookie;
    }
}
