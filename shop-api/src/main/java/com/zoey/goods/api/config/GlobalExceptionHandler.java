package com.zoey.goods.api.config;

import com.zoey.goods.model.consts.GlobalConsts;
import com.zoey.goods.model.exception.OrderException;
import com.zoey.goods.model.exception.StoreException;
import com.zoey.goods.model.result.ApiResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ApiResults unknownException(Exception e) {
        logger.error("程序出现未知异常!msg:{}", e.getMessage());
        return ApiResults.prepare().error(null, GlobalConsts.UNKNOW_FAILURE_CODE,
                "服务器出现未知异常!");
    }

    @ExceptionHandler(value = OrderException.class)
    public ApiResults orderException(OrderException e) {
        logger.error("订单异常,code:{},msg:{},requestId:{}",
                e.getCode(), e.getMessage(), e.getRequestId());
        return ApiResults.prepare().error(null, e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = StoreException.class)
    public ApiResults storeException(StoreException e) {
        logger.error("库存异常,code:{},msg:{},requestId:{}",
                e.getCode(), e.getMessage(), e.getRequestId());
        return ApiResults.prepare().error(null, e.getCode(), e.getMessage());
    }
}
