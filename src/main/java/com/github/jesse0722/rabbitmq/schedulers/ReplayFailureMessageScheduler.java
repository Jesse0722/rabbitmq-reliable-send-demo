package com.github.jesse0722.rabbitmq.schedulers;

import com.github.jesse0722.rabbitmq.rabbitmq.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Lijiajun
 * @date 2020/11/10 18:24
 */
@Slf4j
@Component
public class ReplayFailureMessageScheduler {
    @Autowired
    private BrokerMessageRedisDao brokerMessageRedisDao;
    @Autowired
    private ProducerClient producerClient;

    @Scheduled(cron = "5/10 * * * * ?")
    public void replay(){
        log.info("Begin to replay failure message...");
        brokerMessageRedisDao.getList(MessageStatus.N_ACK).stream().parallel().forEach(brokerMessage -> {
            // 如果当前时间大于重拾时间，且重拾次数小于3次
            if (brokerMessage.getNextRetry().before(new Date()) && brokerMessage.getTryCount() < 3) {
                brokerMessage.setNextRetry(new Date(brokerMessage.getNextRetry().getTime() + 60 * 1000));
                brokerMessage.setTryCount(brokerMessage.getTryCount() + 1);
                brokerMessageRedisDao.save(brokerMessage);

                producerClient.send(brokerMessage.getMessage());
                log.info("Replay send success");
            }
        });
    }
}
