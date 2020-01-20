package com.zoey.goods.consumer.config;

import com.dashuai.commons.utils.JasksonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zoey.goods.consumer.listener.OrderMessageListener;
import com.zoey.goods.model.consts.MqGroupNameConst;
import com.zoey.goods.model.consts.MqTopicConst;
import com.zoey.goods.model.enums.MqTagEnum;
import com.zoey.goods.model.mq.ReliableMessageRegisterDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Configuration
public class MqConfiguration {
    @Autowired
    OrderMessageListener orderMessageListener;
    @Resource
    private TaskExecutor taskExecutor;
    /**
     * The Rocket mq config.
     */
    @Value("${rocketmq.config}")
    String rocketMqConfig;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() throws MQClientException {
        List<ReliableMessageRegisterDto> reliableMessageRegisterDtos = JasksonUtils.json2GenericObject(rocketMqConfig, new TypeReference<List<ReliableMessageRegisterDto>>() {
        });
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(MqGroupNameConst.ORDER_CREATE_CONSUMER_GROUP);
        consumer.setNamesrvAddr(reliableMessageRegisterDtos.get(0).getNamesrvAddr());
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe(MqTopicConst.ORDER_TOPIC, MqTagEnum.CreateOrder.getTag());
        consumer.registerMessageListener(orderMessageListener);
        consumer.setConsumeThreadMax(2);
        consumer.setConsumeThreadMin(2);
        taskExecutor.execute(() -> {
            try {
                Thread.sleep(5000);
                consumer.start();
                log.info("RocketMq OrderMessageConsumer OK.");
            } catch (InterruptedException | MQClientException e) {
                log.error("RocketMq OrderMessageConsumer, 出现异常={}", e.getMessage(), e);
            }
        });
        return consumer;
    }

}
