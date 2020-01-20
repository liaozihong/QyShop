package com.zoey.goods.model;

import lombok.Data;

import java.util.List;

@Data
public class Skus {
    private List<GoodsSku> sku;
    private Integer total;
}
