package com.zoey.goods.model.exception;

import com.zoey.goods.model.enums.ErrorCodeEnum;

/**
 * Created in 2020.03.09
 *
 * @author ZH ·L
 */
public class RegisterFailureException extends BusinessException {
    /**
     * Instantiates a new Register failure exception.
     *
     * @param codeEnum  the code enum
     * @param requestId the request id
     */
    public RegisterFailureException(ErrorCodeEnum codeEnum, String requestId) {
        super(codeEnum, requestId);
    }
}
