package com.zoey.goods.dao.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zoey
 * @since 2020-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qy_order_info")
@Builder
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 买家ID
     */
    private Integer userId;

    /**
     * 运费
     */
    private BigDecimal deliverMoney;

    /**
     * 订单总金额,包括运费
     */
    private BigDecimal totalMoney;

    /**
     * 实际订单总金额	,进行各种折扣之后的金额
     */
    private BigDecimal realTotalMoney;

    /**
     * 购买的商品列表
     */
    private String goodsList;

    /**
     * -3：用户拒收 -2：未付款的订单 -1：用户取消 0：待发货 1：配送中 2：用户确认收货
     */
    private Integer orderStatus;

    /**
     * 支付方式	0：在线支付,1:货到付款
     */
    private Integer payType;

    /**
     * 支付来源	1：支付宝 2：微信
     */
    private Integer payFrom;

    /**
     * 是否支付	0：未支付 1：已支付
     */
    private Integer isPay;

    /**
     * 最后一级区域ID
     */
    private Integer areaId;

    /**
     * 区域ID路径	省级id_市级id_县级Id_ 例如:110000_110100_110101_
     */
    private String areaIdPath;

    /**
     * 收货人名称
     */
    private String userName;

    /**
     * 收件人地址
     */
    private String userAddress;

    /**
     * 收件人手机号
     */
    private String userPhone;

    /**
     * 发票抬头
     */
    private String invoiceClient;

    /**
     * 所得积分
     */
    private Integer orderScore;

    /**
     * 是否需要发票,0不需要，1需要
     */
    private Integer isInvoice;

    /**
     * 订单备注
     */
    private String orderRemark;

    /**
     * 订单来源
     */
    private Integer orderSrc;

    /**
     * 收货时间
     */
    private LocalDateTime receiveTime;

    /**
     * 关闭原因
     */
    private String cancelReason;

    /**
     * 拒收原因
     */
    private String rejectOtherReason;

    /**
     * 是否订单已完结	0：未完结 1：已完结
     */
    private Integer isClosed;

    /**
     * 快递公司
     */
    private Integer expressId;

    /**
     * 快递号
     */
    private String expressNo;

    /**
     * 在线支付流水
     */
    private String tradeNo;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;


}
