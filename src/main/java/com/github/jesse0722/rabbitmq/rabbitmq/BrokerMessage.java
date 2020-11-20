package com.github.jesse0722.rabbitmq.rabbitmq;

import com.github.jesse0722.rabbitmq.constant.MessageConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lijiajun
 * @date 2020/11/10 16:21
 */
@Data
public class BrokerMessage implements Serializable {
    private static final long serialVersionUID = 7447792462810110841L;

    private String messageId;

    private Message message;

    private Integer tryCount;

    private String status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

    public BrokerMessage(Message message) {
        this.messageId = MessageConstant.MESSAGE_PREFIX + message.getMessageId();
        this.message = message;
        Date updateTime = new Date();
        this.tryCount = 0;
        this.createTime = updateTime;
        this.updateTime = updateTime;
        this.status = MessageStatus.N_ACK.toString();
        this.nextRetry = new Date(updateTime.getTime() + 60 * 1000); //60秒后重拾
    }

    public BrokerMessage() {
    }
}
