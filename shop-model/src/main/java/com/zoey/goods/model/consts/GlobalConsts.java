package com.zoey.goods.model.consts;

/**
 * Created in 2019.12.26
 *
 * @author Zoey
 */
public class GlobalConsts {
    /**
     * 成功响应状态码
     */
    public final static int SUCCESS_CODE = 0;
    /**
     * 失败响应状态码
     */
    public final static int FAILURE_CODE = 1;
    /**
     * 未知异常状态码
     */
    public final static int UNKNOW_FAILURE_CODE = 90500;

    /**
     * 登录成功后添加的cookie 名字
     */
    public final static String LOGIN_COOKIE_KEY = "qyshop";

    /**
     * cookie 设置的域名
     */
    public final static String DOMAIN = "localhost";

    /**
     * cookie 设置的域名 , 单位 s
     */
    public final static int LOGIN_COOKIE_TIMEOUT = 7200;

    public interface Number {
        int THOUSAND_INT = 1000;
        int HUNDRED_INT = 100;
        int ONE_INT = 1;
        int TWO_INT = 2;
        int THREE_INT = 3;
        int FOUR_INT = 4;
        int FIVE_INT = 5;
        int SIX_INT = 6;
        int SEVEN_INT = 7;
        int EIGHT_INT = 8;
        int NINE_INT = 9;
        int TEN_INT = 10;
        int EIGHTEEN_INT = 18;
    }
}
