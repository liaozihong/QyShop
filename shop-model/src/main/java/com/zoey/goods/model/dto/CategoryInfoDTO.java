package com.zoey.goods.model.dto;

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
public class CategoryInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类目cid
     */
    private Long cid;

    /**
     * 类目名
     */
    private String name;

    /**
     * 父类目cid
     */
    private Long parentCid;

    /**
     * 是否为父类目，只有非父类目才能发布商品
     */
    private Integer isParent;

    /**
     * 一级类目cid
     */
    private Long cidLev1;

    /**
     * 一级类目名
     */
    private String nameLev1;

    /**
     * 二级类目cid
     */
    private Long cidLev2;

    /**
     * 二级类目名
     */
    private String nameLev2;

    /**
     * 三级类目cid
     */
    private Long cidLev3;

    /**
     * 三级类目名
     */
    private String nameLev3;

    /**
     * 四级类目cid
     */
    private Long cidLev4;

    /**
     * 四级类目名
     */
    private String nameLev4;

    /**
     * 完整类目路径，用于前台显示，即所有级别的name用>隔开；例如：内衣>睡衣、家居服、睡袍、浴袍>睡衣、家居服
     */
    private String catePath;

    /**
     * 完整路径的类目名分词，将用于搜索，用英文逗号隔开（每个节点如果包含特殊字符“/”“_”“、”也需切分）切出来后重复的也要去掉；例如：cath_path="内衣>睡衣、家居服、睡袍、浴袍>睡衣、家居服"==> search_key将切割组合为"内衣,睡衣,家居服,睡袍,浴袍"
     */
    private String searchKey;

    /**
     * 类目被使用次数，命中搜索后+1，预留，搜索结果列表显示的优先级条件
     */
    private Integer used;

    /**
     * 状态，默认值1；可选值:1=normal(正常),0=deleted(删除)
     */
    private Integer status;

    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    private Integer sortOrder;

    /**
     * 最近修改时间
     */
    private LocalDateTime modified;

    /**
     * 创建时间
     */
    private LocalDateTime created;


}
