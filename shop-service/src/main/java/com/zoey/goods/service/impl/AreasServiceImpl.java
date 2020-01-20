package com.zoey.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dashuai.commons.utils.Strings;
import com.zoey.goods.dao.areas.entity.QyAreas;
import com.zoey.goods.dao.areas.mapper.QyAreasMapper;
import com.zoey.goods.model.enums.AreasTypeEnum;
import com.zoey.goods.service.IAreasService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 城市地区表 服务实现类
 * </p>
 *
 * @author zoey ·L
 * @since 2020 -01-19
 */
@Service
@Slf4j
public class AreasServiceImpl extends ServiceImpl<QyAreasMapper, QyAreas> implements IAreasService {

    @Override
    public void getAreasData(String requestId) throws IOException {
        Document doc = Jsoup.connect("http://www.mca.gov.cn/article/sj/xzqh/2019/201901-06/201902061009.html").maxBodySize(0).get();
        Elements elements = doc.getElementsByClass("xl7016597");
        List<String> stringList = elements.eachText();
        List<String> stringName = new ArrayList<>();
        List<String> stringCode = new ArrayList<>();
        for (int i = 0; i < stringList.size(); i++) {
            if (i % 2 == 0) {
                //地区代码
                stringCode.add(stringList.get(i));
            } else {
                //地区名字
                stringName.add(stringList.get(i));
            }
        }
        //正常情况 两个 list size 应该 一样
        if (stringName.size() != stringCode.size()) {
            throw new RuntimeException("数据错误");
        }

        List<QyAreas> qyAreasList = processData(stringName, stringCode);
        boolean isSuccess = this.saveOrUpdateBatch(qyAreasList);
        log.info("批量增加或更新省市县数据{},requestId:{}", isSuccess ? "成功" : "失败", requestId);
    }

    private List<QyAreas> processData(List<String> stringName, List<String> stringCode) {
//        List<Province> provinceList = new ArrayList<>();
        List<QyAreas> qyAreas = new ArrayList<>();
        for (int i = 0; i < stringCode.size(); i++) {
            String provinceName = stringName.get(i);
            String provinceCode = stringCode.get(i);
            if (provinceCode.endsWith("0000")) {
//                Province province = new Province();
//                provinceList.add(province);
//                province.setCode(provinceCode);
//                province.setName(provinceName);
                qyAreas.add(fillQyAreas("", provinceCode, provinceName, AreasTypeEnum.PROVINCE.getType()));
//                List<City> cities = new ArrayList<>();
//                province.setCityList(cities);
                //香港，澳门，台湾，没有市级行政单位划分，城市 地区 和省份保持一致
                if (provinceName.contains("香港") || provinceName.contains("澳门") || provinceName.contains("台湾")) {
//                    City city = new City();
//                    List<Area> areas = new ArrayList<>();
//                    city.setName(provinceName);
//                    city.setCode(provinceCode);
//                    city.setAreaList(areas);
                    qyAreas.add(fillQyAreas(provinceCode, provinceCode, provinceName, AreasTypeEnum.CITY.getType()));
//                    cities.add(city);
//                    Area area = new Area();
//                    area.setName(provinceName);
//                    area.setCode(provinceCode);
//                    areas.add(area);
                    qyAreas.add(fillQyAreas(provinceCode, provinceCode, provinceName, AreasTypeEnum.AREA.getType()));
                }
                //直辖市 城市和省份名称一样
                if (provinceName.contains("北京") || provinceName.contains("上海") || provinceName.contains("天津") || provinceName.contains("重庆")) {
//                    City city = new City();
//                    List<Area> areas = new ArrayList<>();
//                    city.setName(provinceName);
//                    city.setCode(provinceCode);
//                    city.setAreaList(areas);
                    qyAreas.add(fillQyAreas(provinceCode, provinceCode, provinceName, AreasTypeEnum.CITY.getType()));
//                    cities.add(city);
                    //县区
                    for (int k = 0; k < stringCode.size(); k++) {
                        String areaName = stringName.get(k);
                        String areaCode = stringCode.get(k);
                        if (!provinceCode.equals(areaCode) && areaCode.startsWith(provinceCode.substring(0, 2))) {
//                            Area area = new Area();
//                            area.setName(areaName);
//                            area.setCode(areaCode);
                            qyAreas.add(fillQyAreas(provinceCode, areaCode, areaName, AreasTypeEnum.AREA.getType()));
//                            areas.add(area);
                        }
                    }
                }
                for (int j = 0; j < stringCode.size(); j++) {
                    String cityName = stringName.get(j);
                    String cityCode = stringCode.get(j);
                    //遍历获取地级市
                    if (!cityCode.equals(provinceCode) && cityCode.startsWith(provinceCode.substring(0, 2)) && cityCode.endsWith("00")) {
//                        City city = new City();
//                        List<Area> areas = new ArrayList<>();
//                        city.setName(cityName);
//                        city.setCode(cityCode);
//                        city.setAreaList(areas);
                        qyAreas.add(fillQyAreas(provinceCode, cityCode, cityName, AreasTypeEnum.CITY.getType()));
//                        cities.add(city);
                        //遍历获取县区
                        for (int k = 0; k < stringCode.size(); k++) {
                            String areaName = stringName.get(k);
                            String areaCode = stringCode.get(k);
                            if (!areaCode.equals(cityCode) && areaCode.startsWith(cityCode.substring(0, 4))) {
//                                Area area = new Area();
//                                area.setName(areaName);
//                                area.setCode(areaCode);
                                qyAreas.add(fillQyAreas(cityCode, areaCode, areaName, AreasTypeEnum.AREA.getType()));
//                                areas.add(area);
                            }
                        }
                    }
                }
            }
        }
        return qyAreas;
    }

    /**
     * Fill qy areas qy areas.
     *
     * @param parentId the parent id
     * @param code     the code
     * @param name     the name
     * @param type     the type
     * @return the qy areas
     */
    public QyAreas fillQyAreas(String parentId, String code, String name, int type) {
        QyAreas qyAreas = new QyAreas();
        qyAreas.setAreaCode(Integer.valueOf(code));
        qyAreas.setAreaName(name);
        qyAreas.setParentId(Strings.isNullOrEmpty(parentId) ? 0 : Integer.valueOf(parentId));
        qyAreas.setAreaType(type);
        qyAreas.setAreaSort(Integer.valueOf(code));
        return qyAreas;
    }
}
