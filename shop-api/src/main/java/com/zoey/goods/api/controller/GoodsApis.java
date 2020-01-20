package com.zoey.goods.api.controller;

import com.dashuai.commons.utils.UniqueIdUtils;
import com.zoey.goods.model.result.ApiResults;
import com.zoey.goods.service.IGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created in 2019.12.16
 *
 * @author Zoey
 */
@RestController
@RequestMapping("/goods")
public class GoodsApis {

    @Autowired
    private IGoodsInfoService goodsInfoService;

    /**
     * 获取全部商品
     *
     * @return the goods list
     */
    @RequestMapping("/getGoodsAll")
    public ApiResults getGoodsList(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String requestId = UniqueIdUtils.getUUIdStr();
        return ApiResults.prepare().success(goodsInfoService.getGoodsList(pageNo, pageSize, requestId));
    }

    @RequestMapping("/getGoodsInfo")
    public ApiResults getGoodsInfo(@RequestParam Integer gid) {
        String requestId = UniqueIdUtils.getUUIdStr();
        return ApiResults.prepare().success(goodsInfoService.getFullGoodsInfo(gid, requestId));
    }
}

