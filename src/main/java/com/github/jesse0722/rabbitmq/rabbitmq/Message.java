package com.github.jesse0722.rabbitmq.rabbitmq;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lijiajun
 * @date 2020/11/09 16:40
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 841277940410721237L;

    /* 	消息的唯一ID	*/
    private String messageId;

    /*	消息的主题		*/
    private String topic;

    /*	消息的路由规则	*/
    private String routingKey = "";

    /* 消息主体 */
    private Object messageEntity;

    /*	延迟消息的参数配置	*/
    private int delayMills;

    /*	消息类型：默认为RAPID消息类型	*/
    private String messageType;

    public Message() {
    }


    public Message(String messageId, String topic, String routingKey, Object messageEntity, String messageType) {
        this(messageId, topic, routingKey, messageEntity,0, messageType);

    }

    public Message(String messageId, String topic, String routingKey, Object messageEntity) {
        this(messageId, topic, routingKey, messageEntity,0, MessageType.RAPID);

    }

    public Message(String messageId, String topic, String routingKey, Object messageEntity, int delayMills) {
        this(messageId, topic, routingKey, messageEntity, delayMills, MessageType.RAPID);
    }

    public Message(String messageId, String queue, Object messageEntity, String messageType) {
        this(messageId, "", queue, messageEntity, 0, messageType);
    }

    public Message(String messageId, String topic, String routingKey, Object messageEntity, int delayMills,
                   String messageType) {
        this.messageId = messageId;
        this.topic = topic;
        this.routingKey = routingKey;
        this.messageEntity = messageEntity;
        this.delayMills = delayMills;
        this.messageType = messageType;
    }
}
