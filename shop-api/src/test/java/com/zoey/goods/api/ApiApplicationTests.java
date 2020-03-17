package com.zoey.goods.api;

import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zoey.goods.comment.utils.RSAUtils;
import com.zoey.goods.dao.goods.entity.GoodsInfo;
import com.zoey.goods.dao.goods.mapper.GoodsInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = ApiApplication.class)
class ApiApplicationTests {

    @Autowired
    GoodsInfoMapper goodsInfoMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        List<GoodsInfo> goodsInfos = goodsInfoMapper.selectList(null);
        System.out.println(JasksonUtils.toJson(goodsInfos));
    }


    @Test
    public void test() {
        System.out.println(RSAUtils.generateBase64PublicKey());
    }

}
