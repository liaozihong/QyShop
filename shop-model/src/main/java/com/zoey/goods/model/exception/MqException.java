package com.zoey.goods.model.exception;

import com.zoey.goods.model.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MqException extends BusinessException {
    public MqException(int code, String msgFormat, Object... args) {
        super(code, msgFormat, args);
        log.info("<== MqException, code:{}, message:{}", this.code, super.getMessage());
    }

    public MqException(int code, String message) {
        super(code, message);
        log.info("<== MqException, code:{}, message:{}", this.code, super.getMessage());
    }

    public MqException(ErrorCodeEnum codeEnum) {
        super(codeEnum.code(), codeEnum.msg());
        log.info("<== MqException, code:{}, message:{}", this.code, super.getMessage());
    }

    public MqException(ErrorCodeEnum codeEnum, Object... args) {
        super(codeEnum, args);
        log.info("<== MqException, code:{}, message:{}", this.code, super.getMessage());
    }
}
