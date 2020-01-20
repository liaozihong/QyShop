package com.zoey.goods.api.config;

import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zoey.goods.model.mq.ReliableMessageRegisterDto;
import com.zoey.goods.service.mq.MqProducerBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created in 2020.01.14
 *
 * @author ZH ·L
 */
@Component
public class MqBeanInitRunner implements CommandLineRunner {

    /**
     * The Rocket mq config.
     */
    @Value("${rocketmq.config}")
    String rocketMqConfig;

    @Override
    public void run(String... args) throws Exception {
        List<ReliableMessageRegisterDto> producerDtoList = JasksonUtils.json2GenericObject(rocketMqConfig, new TypeReference<List<ReliableMessageRegisterDto>>() {
        });
        producerDtoList.forEach(MqProducerBeanFactory::buildProducerBean);
    }
}
