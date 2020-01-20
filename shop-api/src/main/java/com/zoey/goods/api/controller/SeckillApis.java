package com.zoey.goods.api.controller;

import com.dashuai.commons.utils.UniqueIdUtils;
import com.zoey.goods.model.body.SeckillOrderRequestBody;
import com.zoey.goods.model.result.ApiResults;
import com.zoey.goods.service.IOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class SeckillApis {

    @Autowired
    IOrderInfoService orderInfoService;

    @RequestMapping("/seckillOrder")
    public ApiResults seckillOrder(@RequestBody SeckillOrderRequestBody requestBody) {
        String requestId = UniqueIdUtils.getUUIdStr();
        String orderNo = orderInfoService.createSeckillOrder(10000, requestBody.getGid(),
                requestBody.getSkuId(), requestBody.getBuyerCount(), requestId);
        return ApiResults.prepare().success(orderNo);
    }
}
