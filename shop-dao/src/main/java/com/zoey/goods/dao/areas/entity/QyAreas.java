package com.zoey.goods.dao.areas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 城市地区表
 * </p>
 *
 * @author zoey
 * @since 2020-01-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qy_areas")
public class QyAreas implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父编号
     */
    private Integer parentId;

    /**
     * 地区编号
     */
    private Integer areaCode;
    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 是否显示	0：是 1：否
     */
    private Integer isShow;

    /**
     * 排序号
     */
    private Integer areaSort;

    /**
     * 地区首字母
     */
    private String areaKey;

    /**
     * 级别标志	1：省 2：市 3：县区 4: 街道
     */
    private Integer areaType;

    /**
     * 启用：0  禁用：1
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    private LocalDateTime updateAt;


}
