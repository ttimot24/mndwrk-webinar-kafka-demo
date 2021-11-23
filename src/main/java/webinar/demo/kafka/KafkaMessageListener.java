/*
 * ====================================
 * Copyright (C) 2021 Commsignia Ltd
 * http://commsignia.com
 * All rights reserved
 * ------------------------------------
 * Date: Jan 5, 2021
 * Author: Timót Tarjáni <timot.tarjani@commsignia.com>
 * Project: MessageLake
 * ====================================
 */

package webinar.demo.kafka;

import webinar.demo.config.KafkaConfig;
import webinar.demo.entity.ConsumedEvent;
import lombok.extern.slf4j.Slf4j;
import webinar.demo.service.WebinarDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaMessageListener {

    @Autowired
    private WebinarDemoService webinarDemoService;

    @KafkaListener(topics = KafkaConfig.KAFKA_UPSTREAM_INBOUND_TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void listenInboundEvent(final ConsumedEvent event) {
        log.debug("[Kafka] Consumed event: {}", event);

        this.webinarDemoService.handleMessage(event);

    }






}
