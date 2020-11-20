package com.github.jesse0722.rabbitmq.listener;

import com.github.jesse0722.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Lijiajun
 * @date 2020/11/12 10:14
 */

@Component
public class OrderRabbitListener{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RabbitListener(queues = "queue1")
    public void consumeOrder(Order order) {
        Integer count = (Integer) redisTemplate.opsForValue().get("count");
        redisTemplate.opsForValue().set("count", count+1);

    }

    @RabbitListener(queues = "queue2")
    public void consumeOrder2(Order order) {
        System.out.println(order);
    }
}
