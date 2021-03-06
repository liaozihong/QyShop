package com.zoey.goods.model.result;

import lombok.Data;

import java.util.List;

/**
 * Created in 2020.01.19
 *
 * @author ZH ·L
 */
@Data
public class City {
    private String code;
    private String name;
    private List<Area> areaList;
}
