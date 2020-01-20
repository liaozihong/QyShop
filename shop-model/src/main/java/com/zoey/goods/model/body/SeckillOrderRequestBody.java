package com.zoey.goods.model.body;

import lombok.Data;

@Data
public class SeckillOrderRequestBody {
    private Integer gid;
    private Long skuId;
    private Integer buyerCount;
}
