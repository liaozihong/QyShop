package com.zoey.goods.model.exception;


import com.zoey.goods.model.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;

/**
 * 业务异常.
 *
 * @author ZH ·L
 */
@Slf4j
public class BusinessException extends RuntimeException {

    /**
     * 异常码
     */
    protected int code;

    /**
     * The Request id.
     */
    protected String requestId;

    private static final long serialVersionUID = 3160241586346324994L;

    /**
     * Instantiates a new Business exception.
     */
    public BusinessException() {
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param cause the cause
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code    the code
     * @param message the message
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code      the code
     * @param message   the message
     * @param requestId the request id
     */
    public BusinessException(int code, String message, String requestId) {
        super(message);
        this.code = code;
        this.requestId = requestId;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param code      the code
     * @param msgFormat the msg format
     * @param args      the args
     */
    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param codeEnum  the code enum
     * @param requestId the request id
     */
    public BusinessException(ErrorCodeEnum codeEnum, String requestId) {
        super(codeEnum.msg());
        this.code = codeEnum.code();
        this.requestId = requestId;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param codeEnum the code enum
     * @param args     the args
     */
    public BusinessException(ErrorCodeEnum codeEnum, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param codeEnum  the code enum
     * @param requestId the request id
     * @param args      the args
     */
    public BusinessException(ErrorCodeEnum codeEnum, String requestId, Object... args) {
        super(String.format(codeEnum.msg(), args));
        this.code = codeEnum.code();
        this.requestId = requestId;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * Gets request id.
     *
     * @return the request id
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * Sets request id.
     *
     * @param requestId the request id
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
