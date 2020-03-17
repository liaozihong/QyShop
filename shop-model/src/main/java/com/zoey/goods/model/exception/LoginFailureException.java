package com.zoey.goods.model.exception;

import com.zoey.goods.model.enums.ErrorCodeEnum;

public class LoginFailureException extends BusinessException {
    public LoginFailureException(ErrorCodeEnum codeEnum, String requestId) {
        super(codeEnum, requestId);
    }
}
