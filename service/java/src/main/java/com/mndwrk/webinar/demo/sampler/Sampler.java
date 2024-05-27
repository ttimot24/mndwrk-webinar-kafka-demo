package com.mndwrk.webinar.demo.sampler;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.mndwrk.webinar.demo.config.KafkaConfig;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import com.mndwrk.webinar.demo.entity.Event;;
import com.mndwrk.webinar.demo.ksqldb.AvroConsumedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Sampler implements InitializingBean {

    @Autowired
    private KafkaTemplate<String, AvroConsumedEvent> kafkaTemplate;

    private List<String> sources = List.of("TLC", "CCTV", "SENSOR");

    @Override
    public void afterPropertiesSet() {
        log.info("Sampler started!");
    }

    @Async
    @Scheduled(cron = "* * * * * *")
    public void sampler() {

        AvroConsumedEvent producedEvent = AvroConsumedEvent
                .newBuilder()
                .setUuid(UUID.randomUUID().toString())
                .setDetectedAt(Instant.now())
                .setSource("SAMPLER")
                .setSummary("Event from sampler")
                .build();

      /*  Event producedEvent = ConsumedEvent.builder().uuid(UUID.randomUUID()).detectedAt(OffsetDateTime.now()).source(getRandom(sources))
                .summary("Event from sampler").build(); */

        kafkaTemplate.send(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, producedEvent);

    }

    public static String getRandom(List<String> list) {
        int rnd = new Random().nextInt(list.size());
        return list.get(rnd);
    }


}
