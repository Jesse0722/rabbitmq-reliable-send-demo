package com.github.jesse0722.rabbitmq.rabbitmq;

import java.util.List;

/**
 * @author Lijiajun
 * @date 2020/11/09 15:20
 *
 */
public interface RabbitBroker {

	void rapidSend(Message message);

	void reliantSend(Message message);

	void sendMessages(List<Message> messages);

}
