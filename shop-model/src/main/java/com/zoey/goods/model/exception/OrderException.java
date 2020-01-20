package com.zoey.goods.model.exception;

import com.zoey.goods.model.enums.ErrorCodeEnum;

/**
 * Created in 2019.12.26
 * 订单系列异常
 *
 * @author Zoey
 */
public class OrderException extends BusinessException {

    public OrderException(ErrorCodeEnum codeEnum, String requestId) {
        super(codeEnum, requestId);
    }

    public OrderException(ErrorCodeEnum codeEnum, String requestId, Object... args) {
        super(codeEnum, requestId, args);
    }
}
