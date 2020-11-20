package com.github.jesse0722.rabbitmq.rabbitmq;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Lijiajun
 * @date 2020/11/09 15:59
 *
 */
@Slf4j
@Component
public class RabbitBrokerImpl implements RabbitBroker {

	@Autowired
	private RabbitTemplateContainer rabbitTemplateContainer;
	@Autowired
	private BrokerMessageRedisDao brokerMessageRedisDao;

	@Override
	public void reliantSend(Message message) {
		//1. 把消息保存到redis中
		BrokerMessage brokerMessage = new BrokerMessage(message);
		brokerMessageRedisDao.save(brokerMessage);
		//2. 执行真正的发送消息逻辑
		sendKernel(message);
	}

	@Override
	public void sendMessages(List<Message> messages) {

	}

	/**
	 * 	$rapidSend迅速发消息
	 */
	@Override
	public void rapidSend(Message message) {
		sendKernel(message);
	}

	/**
	 * 	$sendKernel 发送消息的核心方法 使用异步线程池进行发送消息
	 * @param message
	 */
	private void sendKernel(Message message) {
		CorrelationData correlationData =
				new CorrelationData(message.getMessageId());

		RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
		rabbitTemplate.correlationConvertAndSend(message.getMessageEntity(), correlationData);
		log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());

	}



}
