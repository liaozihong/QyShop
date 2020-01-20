package com.zoey.goods.api;

import com.zoey.goods.service.IAreasService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = ApiApplication.class)
public class AreasServiceTest {

    @Autowired
    private IAreasService areasService;

    @Test
    public void testGetAreasData() {
        try {
            areasService.getAreasData(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
