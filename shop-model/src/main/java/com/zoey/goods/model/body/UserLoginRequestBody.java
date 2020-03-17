package com.zoey.goods.model.body;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created in 2020.03.05
 *
 * @author ZH ·L
 */
@Data
public class UserLoginRequestBody implements Serializable {
    private static final long serialVersionUID = -2706711938651041126L;
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
}
