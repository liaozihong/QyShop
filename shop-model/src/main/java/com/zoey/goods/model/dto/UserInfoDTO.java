package com.zoey.goods.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created in 2020.03.03
 *
 * @author ZH ·L
 */
@Data
public class UserInfoDTO implements Serializable {
    private static final long serialVersionUID = 9187889792576983469L;
    private Integer userId;

    /**
     * 买家名字
     */
    private String userNick;

    /**
     * 性别,0男，1女
     */
    private Integer sex;

    /**
     * 出生年月
     */
    private LocalDate birth;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;
    /**
     * 身份证号
     */
    private String identityCard;

    /**
     * QQ号
     */
    private Integer qqNum;

    /**
     * 微信号
     */
    private String wechatNum;

    /**
     * 支付宝账号
     */
    private String zfbNum;

}
