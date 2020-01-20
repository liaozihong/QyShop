package com.zoey.goods.model.result;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created in 2019.12.18
 *
 * @author Zoey
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsResult {
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
     * 类目名
     */
    private String goodsCname;
    /**
     * 类目路径
     */
    private String catePath;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime putawayTime;

    /**
     * 宝贝下架时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime soldOutTime;

    /**
     * 宝贝状态：1=正常上架，-1=正常下架（仓库中），-99=软删除
     */
    private Integer goodsStatus;
    /**
     * 是否开启秒杀，0，不开启，1开启
     */
    private Integer isEnableSeckill;

    /**
     * 秒杀开启时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime seckillStartTime;

    /**
     * 秒杀结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime seckillEndTime;
    /**
     * 限制购买数
     */
    private Integer limitBuyerCount;
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

}
