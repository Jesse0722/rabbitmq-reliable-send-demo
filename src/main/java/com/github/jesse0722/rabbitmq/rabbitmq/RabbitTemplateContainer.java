package com.github.jesse0722.rabbitmq.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lijiajun
 * @date 2020/11/10 15:23
 */
@Component
@Slf4j
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback{

    //不同topic使用不同的template，提高性能
    private final Map<String, RabbitTemplate> rabbitMap = new ConcurrentHashMap<>();

    @Autowired
    private BrokerMessageRedisDao brokerMessageRedisDao;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private ConnectionFactory connectionFactory;

    public RabbitTemplate getTemplate(Message message) {
        if(message == null) {
            throw new RuntimeException("message can not be null");
        }
        String topic = message.getTopic() != null ? message.getTopic() : "";
        RabbitTemplate rabbitTemplate = rabbitMap.get(topic);
        if(rabbitTemplate != null) {
            return rabbitTemplate;
        }
        rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(topic);
        rabbitTemplate.setRoutingKey(message.getRoutingKey());
        rabbitTemplate.setRetryTemplate(new RetryTemplate());
        if(!MessageType.RAPID.equals(message.getMessageType())) {
            rabbitTemplate.setConfirmCallback(this);
        }

        rabbitTemplate.setMessageConverter(messageConverter);

        rabbitMap.putIfAbsent(topic,rabbitTemplate);
        return rabbitTemplate;
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack) {
            //删除redis消息
            log.info("send message is OK, confirm messageId: {}", correlationData.getId());
            brokerMessageRedisDao.delete(correlationData.getId());
            log.info("Delete message cache success, messageId: {}", correlationData.getId());

        } else {
            log.error("send message failed: MessageId:{}, cause: {}", correlationData.getId(), cause);
        }
    }

}
