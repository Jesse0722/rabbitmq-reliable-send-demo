package com.github.jesse0722.rabbitmq.rabbitmq;

import com.github.jesse0722.rabbitmq.constant.MessageConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Lijiajun
 * @date 2020/11/10 17:17
 */
@Slf4j
@Service
public class BrokerMessageRedisDao {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void save(BrokerMessage brokerMessage) {
        redisTemplate.opsForHash().put(MessageConstant.MESSAGES_TABLE, brokerMessage.getMessageId(), brokerMessage);
    }

    public BrokerMessage getByMessageId(String messageId) {
        return (BrokerMessage) redisTemplate.opsForHash().get(MessageConstant.MESSAGES_TABLE, MessageConstant.MESSAGE_PREFIX + messageId);
    }

    public BrokerMessage getByKey(String key) {
        return (BrokerMessage) redisTemplate.opsForHash().get(MessageConstant.MESSAGES_TABLE, key);
    }

    public void delete(String messageId) {
        redisTemplate.opsForHash().delete(MessageConstant.MESSAGES_TABLE, MessageConstant.MESSAGE_PREFIX + messageId);
    }

    /***
     * 批量获取消息
     * @param status
     * @return
     */
    public List<BrokerMessage> getList(String status) {
        List<Object> messages = redisTemplate.opsForHash().values(MessageConstant.MESSAGES_TABLE);
        return messages.parallelStream().map(item -> (BrokerMessage) item).filter(message -> message.getStatus().equals(status)).collect(Collectors.toList());
    }

}
