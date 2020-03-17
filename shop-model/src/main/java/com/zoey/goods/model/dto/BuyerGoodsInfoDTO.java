package com.zoey.goods.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created in 2019.12.26
 * 购买的商品信息，是一个集合，为后面购物车做准备
 *
 * @author Zoey
 */
@Data
@Builder
public class BuyerGoodsInfoDTO implements Serializable {
    private Integer gid;
    private Long skuId;
    private String colorName;
    private String sizeName;
    private BigDecimal price;
    private BigDecimal totlePrice;
    private Integer buyerCount;

}
