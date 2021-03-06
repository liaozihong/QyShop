package com.zoey.goods.model.enums;


/**
 * Created in 2020.01.02
 *
 * @author ZH ·L
 */
public enum ErrorCodeEnum {
    /**
     * Goods 300001 error code enum.
     */
    GOODS300001(300001, "该商品不存在SKU信息!"),
    /**
     * Order 100001 error code enum.
     */
    ORDER100001(100001, "秒杀订单生成失败!"),
    ORDER100002(100002, "秒杀订单生成失败!"),
    /**
     * Store 200001 error code enum.
     */
    STORE200001(200001, "当前商品库存不足!"),
    /**
     * Store 200002 error code enum.
     */
    STORE200002(200003, "商品库存修改失败"),
    /**
     * Store 200003 error code enum.
     */
    STORE200003(200004, "该商品SKU对于的库存不存在!"),
    /**
     * Mq 6000001 error code enum.
     */
    MQ6000001(6000001, "延迟级别错误, Topic=%s, MessageKey=%s"),
    /**
     * Mq 6000002 error code enum.
     */
    MQ6000002(6000002, "Mq编码转换异常, MessageKey=%s"),
    /**
     * Mq 6000003 error code enum.
     */
    MQ6000003(6000003, "MQ重试三次,仍然发送失败, Topic=%s, MessageKey=%s"),

    CACHE8000001(8000001, "缓存操作出现故障"),

    LOGIN900001(900001, "未登录,请前往登录"),
    LOGIN900002(900002, "该用户不存在"),
    LOGIN900003(900003, "密码不正确"),
    LOGIN900004(900004, "登录失败过多，账号已被锁定"),
    LOGIN900005(900005, "系统故障，令牌生成失败!"),
    LOGIN900006(900006, "登录信息过期,请重新登录"),

    REGISTER800001(800001, "注册失败，该用户已存在"),
    REGISTER800002(800002, "注册失败，该邮箱已被注册");


    private int code;
    private String msg;

    /**
     * Msg string.
     *
     * @return the string
     */
    public String msg() {
        return msg;
    }

    /**
     * Code int.
     *
     * @return the int
     */
    public int code() {
        return code;
    }

    ErrorCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Gets enum.
     *
     * @param code the code
     * @return the enum
     */
    public static ErrorCodeEnum getEnum(int code) {
        for (ErrorCodeEnum ele : ErrorCodeEnum.values()) {
            if (ele.code() == code) {
                return ele;
            }
        }
        return null;
    }
}
