/*
 * ====================================
 * Copyright (C) 2020  Commsignia Ltd
 * http://commsignia.com
 * All rights reserved
 * ------------------------------------
 * Date: Oct 6, 2020
 * Author: Timót Tarjáni <timot.tarjani@commsignia.com>
 * Project: MessageLake
 * ====================================
 */

package webinar.demo.service;

import webinar.demo.config.KafkaConfig;
import webinar.demo.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Slf4j
@Service
public class WebinarDemoService {

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void handleMessage(final Event event) {
        event.setUuid(UUID.randomUUID());
        event.setDetectedAt(OffsetDateTime.now());

        log.info("Consumed event {}", event);

        kafkaTemplate.send(KafkaConfig.KAFKA_UPSTREAM_OUTBOUND_TOPIC_NAME, event.getSource(), event);

    }


}
