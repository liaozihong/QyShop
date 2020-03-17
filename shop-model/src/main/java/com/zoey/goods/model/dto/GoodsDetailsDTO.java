package com.zoey.goods.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zoey
 * @since 2019-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品ID
     */
    private Integer gid;

    /**
     * 商品唯一标识
     */
    private String gidUnique;

    /**
     * 商品sku信息
     */
    private String skuJson;

    /**
     * 商品属性
     */
    private String propsNames;

    /**
     * 自定义输入的属性pid
     */
    private String inputPids;

    /**
     * 自定义输入的属性字符串
     */
    private String inputStr;

    /**
     * 商品详情
     */
    private String goodsDesc;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;


}
