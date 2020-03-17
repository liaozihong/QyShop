package com.zoey.goods.model.body;

import lombok.Data;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Created in 2020.03.05
 *
 * @author ZH ·L
 */
@Data
public class UserRegisterRequestBody {
    /**
     * 买家名字
     */
    @NotNull(message = "用户名不能为空")
    private String userNick;

    /**
     * 买家密码
     */
    @NotNull(message = "密码不能为空")
    private String userPassword;

    /**
     * 性别,0男，1女
     */
    @NotNull(message = "性别不能为空")
    private Integer sex;

    /**
     * 出生年月
     */
    @NotNull(message = "生日不能为空")
    private LocalDate birth;

    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    @Email(message = "不满足邮箱格式")
    private String email;

    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String phone;

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
