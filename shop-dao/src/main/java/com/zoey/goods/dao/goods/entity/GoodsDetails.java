package com.zoey.goods.dao.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("qy_goods_details")
public class GoodsDetails implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateAt;


}
