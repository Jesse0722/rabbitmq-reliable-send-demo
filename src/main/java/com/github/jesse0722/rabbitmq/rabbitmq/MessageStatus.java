package com.github.jesse0722.rabbitmq.rabbitmq;

/**
 * 消息投递状态
 * @author Lijiajun
 * @date 2020/11/10 17:21
 */
public class MessageStatus {

    /* 未确认 */
    public static final String N_ACK = "N_ACK";

    /* 已确认 */
    public static final String ACK = "ACK";

    /* 发送失败 */
    public static final String FAILED = "FAILED";
}
