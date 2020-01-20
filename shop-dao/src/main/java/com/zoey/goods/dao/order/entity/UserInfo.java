package com.zoey.goods.dao.order.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zoey
 * @since 2019-12-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qy_user_info")
public class UserInfo implements Serializable {

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
     * 性别
     */
    private String sex;

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
    private Integer phone;

    /**
     * QQ号
     */
    private Integer qqNum;

    /**
     * 微信号
     */
    private String wechatNum;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;


}
