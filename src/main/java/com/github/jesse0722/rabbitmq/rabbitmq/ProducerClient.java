package com.github.jesse0722.rabbitmq.rabbitmq;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author Lijiajun
 * @date 2020/11/09 16:40
 *
 */
@Component
public class ProducerClient {

	@Autowired
	private RabbitBroker rabbitBroker;

//	@Override
	public void send(Message message) throws MessageRunTimeException {
//		Assert.notNull(message.getTopic());
		String messageType = message.getMessageType();
		switch (messageType) {
			case MessageType.RAPID:
				rabbitBroker.rapidSend(message);
				break;
			case MessageType.RELIANT:
				rabbitBroker.reliantSend(message);
				break;
		default:
			break;
		}
	}



}
