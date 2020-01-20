package com.zoey.goods.consumer.listener;

import com.dashuai.commons.api.RedisCacheApi;
import com.dashuai.commons.redis.RedisPool;
import com.dashuai.commons.utils.Strings;
import com.zoey.goods.model.consts.MqTopicConst;
import com.zoey.goods.model.mq.MqMessage;
import com.zoey.goods.service.IOrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * Created in 2020.01.16
 *
 * @author ZH ·L
 */
@Slf4j
@Component
public class OrderMessageListener implements MessageListenerConcurrently {
    @Autowired
    private RedisCacheApi redisCacheApi;
    @Autowired
    private RedisPool redisPool;

    @Autowired
    private IOrderInfoService orderInfoService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        MessageExt msg = messageExtList.get(0);
        String body = new String(msg.getBody());
        String topicName = msg.getTopic();
        String tags = msg.getTags();
        String keys = msg.getKeys();
        log.info("MQ消费Topic={},tag={},key={}", topicName, tags, keys);
        // 控制幂等性使用的key
        try {
            MqMessage.checkMessage(body, topicName, tags, keys);
            String mqKV = null;
            if (redisPool.isExistKey(keys)) {
                mqKV = redisCacheApi.getString(keys);
            }
            if (!Strings.isNullOrEmpty(mqKV)) {
                log.error("MQ消费Topic={},tag={},key={}, 重复消费", topicName, tags, keys);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            if (MqTopicConst.ORDER_TOPIC.equals(topicName)) {
                orderInfoService.handlerOrderGenerate(body, keys);
            }
        } catch (IllegalArgumentException ex) {
            log.error("校验MQ message 失败 ex={}", ex.getMessage(), ex);
        } catch (Exception e) {
            log.error("处理MQ message 失败 topicName={}, keys={}, ex={}", topicName, keys, e.getMessage(), e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        redisCacheApi.setString(keys, keys, 10 * 24 * 60 * 60);
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
