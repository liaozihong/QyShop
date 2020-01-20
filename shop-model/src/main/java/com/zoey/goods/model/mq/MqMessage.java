package com.zoey.goods.model.mq;

import com.google.common.base.Preconditions;
import com.zoey.goods.model.enums.ErrorCodeEnum;
import com.zoey.goods.model.exception.BusinessException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Created in 2020.01.02
 *
 * @author ZH ·L
 */
@Data
@Slf4j
public class MqMessage implements Serializable {
    private String topic;
    private String tag;
    private String key;
    private String body;

    /**
     * Check message message.
     *
     * @param body  the body
     * @param topic the topic
     * @param tag   the tag
     * @param key   the key
     * @return the message
     */
    public static Message checkMessage(String body, String topic, String tag, String key) {
        checkMessage(topic, key, body);
        return buildMessage(body, topic, tag, key);

    }

    /**
     * Check message message.
     *
     * @param message the message
     * @return the message
     */
    public static Message checkMessage(Message message) {
        String body = new String(message.getBody());
        String topic = message.getTopic();
        String key = message.getKeys();
        String tag = message.getTags();
        checkMessage(topic, key, body);
        return buildMessage(body, topic, tag, key);

    }

    private static Message buildMessage(String body, String topic, String tag, String key) {
        Message message = new Message();
        try {
            message.setBody(body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            log.error("编码转换,出现异常={}", e.getMessage(), e);
            throw new BusinessException(ErrorCodeEnum.MQ6000002);
        }
        message.setKeys(key);
        message.setTopic(topic);
        message.setTags(tag);
        return message;
    }

    /**
     * Check message.
     *
     * @param topic the topic
     * @param key   the key
     * @param body  the body
     */
    public static void checkMessage(String topic, String key, String body) {
        Preconditions.checkArgument(!StringUtils.isEmpty(topic), "发送消息失败, 消息主题不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(key), "发送消息失败, 消息关键字不能为空");
        Preconditions.checkArgument(!StringUtils.isEmpty(body), "发送消息失败, 消息体不能为空");
    }
}
