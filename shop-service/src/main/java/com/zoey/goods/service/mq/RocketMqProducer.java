package com.zoey.goods.service.mq;

import com.zoey.goods.model.enums.DelayLevelEnum;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.exception.MqException;
import com.zoey.goods.model.mq.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * Created in 2020.01.14
 *
 * @author ZH ·L
 */
@Slf4j
public class RocketMqProducer {

    private static final int PRODUCER_RETRY_TIMES = 3;

    /**
     * Send simple message send result.
     *
     * @param body       the body
     * @param topic      the topic
     * @param tag        the tag
     * @param key        the key
     * @param delayLevel the delay level
     * @return the send result
     */
    public static SendResult sendSimpleMessage(String body, String topic, String tag, String key, String pid, Integer delayLevel) {
        if (delayLevel == null) {
            delayLevel = 0;
        }
        Message message = MqMessage.checkMessage(body, topic, tag, key);
        if (delayLevel < 0 || delayLevel > DelayLevelEnum.EIGHTEEN.delayLevel()) {
            throw new MqException(ErrorCodeEnum.MQ6000001, topic, key);
        }
        message.setDelayTimeLevel(delayLevel);
        return retrySendMessage(pid, message);
    }

    private static SendResult retrySendMessage(String pid, Message msg) {
        int iniCount = 1;
        SendResult result;
        while (true) {
            try {
                result = MqProducerBeanFactory.getBean(pid).send(msg);
                break;
            } catch (Exception e) {
                log.error("发送消息失败:", e);
                if (iniCount++ >= PRODUCER_RETRY_TIMES) {
                    throw new MqException(ErrorCodeEnum.MQ6000003, msg.getTopic(), msg.getKeys());
                }
            }
        }
        log.info("<== 发送MQ SendResult={}", result);
        return result;
    }
}
