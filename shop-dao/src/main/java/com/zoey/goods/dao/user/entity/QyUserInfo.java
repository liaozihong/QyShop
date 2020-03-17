package com.zoey.goods.dao.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zoey
 * @since 2020-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qy_user_info")
public class QyUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 买家id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    /**
     * 买家名字
     */
    private String userNick;

    /**
     * 买家密码
     */
    private String userPassword;

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

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;


}
