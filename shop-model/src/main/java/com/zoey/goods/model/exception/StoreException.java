package com.zoey.goods.model.exception;

import com.zoey.goods.model.enums.ErrorCodeEnum;

/**
 * Created in 2019.12.30
 * 库存异常
 *
 * @author Zoey
 */
public class StoreException extends BusinessException {
    public StoreException(ErrorCodeEnum codeEnum, String requestId) {
        super(codeEnum, requestId);
    }
}