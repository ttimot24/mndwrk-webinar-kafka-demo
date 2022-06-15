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

package com.mndwrk.webinar.demo.kafka;

import com.mndwrk.webinar.demo.config.KafkaConfig;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import lombok.extern.slf4j.Slf4j;
import com.mndwrk.webinar.demo.service.WebinarDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("kafka-simple")
public class KafkaMessageListener {

    @Autowired
    private WebinarDemoService webinarDemoService;

    @KafkaListener(topics = KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, containerFactory = "kafkaListenerContainerFactory")
    public void listenInboundEvent(final ConsumedEvent event) {
        log.debug("[Kafka] Consumed event: {}", event);

        this.webinarDemoService.handleMessage(event);

    }


}
