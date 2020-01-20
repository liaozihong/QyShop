package com.zoey.goods.model.result;

import com.zoey.goods.model.consts.GlobalConsts;

import java.util.HashMap;

/**
 * Created in 2019.12.16
 *
 * @author Zoey
 */
public class ApiResults extends HashMap<String, Object> {

    /**
     * 获取实例
     *
     * @return 实例 api result
     */
    public static ApiResults prepare() {
        return new ApiResults();
    }

    /**
     * Instantiates a new Api result.
     */
    public ApiResults() {
    }

    /**
     * 调用成功返回结果
     *
     * @param data the data
     * @return 响应实体 api result
     */
    public ApiResults success(Object data) {
        this.put("data", data);
        this.put("code", GlobalConsts.SUCCESS_CODE);
        this.put("msg", "");
        return this;
    }

    /**
     * 调用成功返回结果
     *
     * @param data the data
     * @param code 说明码
     * @param msg  其他信息
     * @return 响应实体 api result
     */
    public ApiResults success(Object data, int code, String msg) {
        this.put("data", data);
        this.put("code", code);
        this.put("msg", msg);
        return this;
    }

    /**
     * 调用失败返回结果
     *
     * @param data the data
     * @param code 错误码
     * @param msg  其他信息
     * @return 响应实体 api result
     */
    public ApiResults error(Object data, int code, String msg) {
        this.put("data", data);
        this.put("code", code);
        this.put("msg", msg);
        return this;
    }
}