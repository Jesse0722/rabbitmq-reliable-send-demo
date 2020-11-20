package com.github.jesse0722.rabbitmq.controller;

import com.github.jesse0722.rabbitmq.entity.Order;
import com.github.jesse0722.rabbitmq.entity.Product;
import com.github.jesse0722.rabbitmq.rabbitmq.Message;
import com.github.jesse0722.rabbitmq.rabbitmq.MessageType;
import com.github.jesse0722.rabbitmq.rabbitmq.ProducerClient;
import com.github.jesse0722.rabbitmq.utils.JsonMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lijiajun
 * @date 2020/11/09 16:49
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ProducerClient producerClient;

    @GetMapping("send")
    public String send() {
        Order order = new Order();
        order.setId("123");
        order.setName("python入门课");
        order.setProducts(Arrays.asList(new Product("iphone11", 1), new Product("iphone12",2)));

        Message message = new Message(UUID.randomUUID().toString(),"order-exchange", "hello", order, MessageType.RELIANT);
        producerClient.send(message);
        return "OK";
    }
}
