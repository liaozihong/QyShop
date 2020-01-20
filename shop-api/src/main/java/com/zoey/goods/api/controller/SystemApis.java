package com.zoey.goods.api.controller;

import com.zoey.goods.model.result.ApiResults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created in 2019.12.20
 *
 * @author Zoey
 */
@RestController
public class SystemApis {
    /**
     * 获取当前时间.
     *
     * @return the current date
     */
    @RequestMapping("/getCurrentDate")
    public ApiResults getCurrentDate() {
        return ApiResults.prepare().success(new Date());
    }
}
