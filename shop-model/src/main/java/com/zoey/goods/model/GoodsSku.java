package com.zoey.goods.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class GoodsSku {
    private int num;
    private int price;
    private int price2;
    @JsonAlias("sku_id")
    private long skuId;
    @JsonAlias("img_url")
    private String imgUrl;
    @JsonAlias("out_id")
    private String outerId;
    private String properties;
    @JsonAlias("properties_name")
    private String propertiesName;

}
