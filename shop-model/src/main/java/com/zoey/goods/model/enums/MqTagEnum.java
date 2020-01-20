package com.zoey.goods.model.enums;

/**
 * Created in 2020.01.15
 *
 * @author ZH ·L
 */
public enum MqTagEnum {
    /**
     * Create order mq tag enum.
     */
    CreateOrder("create_order", "订单创建");

    private String tag;
    private String remark;

    MqTagEnum(String tag, String remark) {
        this.tag = tag;
        this.remark = remark;
    }

    public String getTag() {
        return tag;
    }

    public String getRemark() {
        return remark;
    }
}
