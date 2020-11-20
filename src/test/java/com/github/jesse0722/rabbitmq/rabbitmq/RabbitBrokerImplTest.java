package com.github.jesse0722.rabbitmq.rabbitmq;

import com.github.jesse0722.rabbitmq.entity.Order;
import com.github.jesse0722.rabbitmq.entity.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.*;

/**
 * @author Lijiajun
 * @date 2020/11/10 16:46
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RabbitBrokerImplTest {
    @Autowired
    private BrokerMessageRedisDao brokerMessageRedisDao;
    @Autowired
    private ProducerClient producerClient;

    @Test
    public void putAll() {
        Message message = new Message();
        message.setMessageId("123");
        message.setMessageType(MessageType.RELIANT);
        message.setTopic("order-topic");
        message.setRoutingKey("hello");
        Order order = new Order("1002", "iphone12");
        order.setProducts(Arrays.asList(new Product("asd",1)));
        message.setMessageEntity(order);

        BrokerMessage brokerMessage = new BrokerMessage(message);

        brokerMessageRedisDao.save(brokerMessage);
    }

    @Test
    public void getList() throws IOException {
        List<BrokerMessage> list = brokerMessageRedisDao.getList(MessageStatus.N_ACK.toString());
        list.forEach(item -> System.out.println(item.getMessageId()));
    }

    @Test
    public void get() {
        System.out.println(brokerMessageRedisDao.getByMessageId("123"));
    }

    @Test
    public void delete() {
        brokerMessageRedisDao.delete("123");
    }


    @Test
    public void send() {
        Order order = new Order("123132", "hello");
        order.setProducts(Arrays.asList(new Product("hell",1), new Product("asd",2)));
        Message message = new Message(UUID.randomUUID().toString(), "queue2", order,  MessageType.RELIANT);
        producerClient.send(message);
    }

}
