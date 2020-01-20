package com.zoey.goods.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class GoodsInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Integer gid;

    /**
     * 商品唯一标识
     */
    private String gidUnique;

    /**
     * 店铺ID商品所属店铺
     */
    private Integer sid;

    /**
     * 宝贝叶子类目id
     */
    private Long goodsCid;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品货号
     */
    private String goodsNo;

    /**
     * 宝贝第一张主图的url
     */
    private String goodsPicUrl;

    /**
     * 主图列表中的长图。即item_imgs中position=6
     */
    private String goodsPicUrlLong;

    /**
     * 宝贝主图列表, 英文逗号隔开
     */
    private String goodsPicUrlList;

    /**
     * 商品原价格
     */
    private BigDecimal goodsPrice;

    /**
     * 商品折扣价
     */
    private BigDecimal goodsDiscountPrice;

    /**
     * 商品总库存
     */
    private Integer goodsNums;

    /**
     * 颜色标签集
     */
    private String tagColors;

    /**
     * 尺寸标签集
     */
    private String tagSizes;

    /**
     * 宝贝上架时间
     */
    private LocalDateTime putawayTime;

    /**
     * 宝贝下架时间
     */
    private LocalDateTime soldOutTime;

    /**
     * 宝贝状态：1=正常上架，-1=正常下架（仓库中），-99=软删除
     */
    private Integer goodsStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;


}
