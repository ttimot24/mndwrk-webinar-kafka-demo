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

package meetup.demo.service;

import meetup.demo.config.KafkaConfig;
import meetup.demo.entity.Event;
import lombok.extern.slf4j.Slf4j;
import meetup.demo.entity.ProducedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class MeetupDemoService{

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void handleMessage(final Event event) {
        event.setUuid(UUID.randomUUID());

        log.info("Consumed event {}", event);

        //kafkaTemplate.send(KafkaConfig.KAFKA_UPSTREAM_OUTBOUND_TOPIC_NAME, event.getSource(), event);

    }


}
