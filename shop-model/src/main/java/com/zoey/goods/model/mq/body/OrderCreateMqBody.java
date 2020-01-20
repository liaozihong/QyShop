package com.zoey.goods.model.mq.body;

import lombok.*;

import java.io.Serializable;

/**
 * Created in 2020.01.14
 *
 * @author ZH ·L
 */
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCreateMqBody implements Serializable {

    private static final long serialVersionUID = -6027689277211016083L;
    private String orderNo;
    private Integer buyerCount;
    private Long skuId;
    private Integer gid;
    private Integer userId;
    private String requestId;
}
