package com.mndwrk.webinar.demo.kafka;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mndwrk.webinar.demo.config.KafkaConfig;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import com.mndwrk.webinar.demo.entity.JoinedEvent;
import com.mndwrk.webinar.demo.entity.ProducedEvent;
import com.mndwrk.webinar.demo.transformer.EventTypeTransformer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("kafka-streams")
public class KafkaStreamsCustomizer implements KafkaStreamsInfrastructureCustomizer {

    final Serde<String> keySerde;
    final Serde<ConsumedEvent> consumedValueSerde;
    final Serde<ProducedEvent> producedValueSerde;
    final Serde<JoinedEvent> joinedValueSerde;

    public KafkaStreamsCustomizer(final ObjectMapper objectMapper) {
        keySerde = Serdes.String();
        consumedValueSerde = new JsonSerde<>(ConsumedEvent.class, objectMapper);
        producedValueSerde = new JsonSerde<>(ProducedEvent.class, objectMapper);
        joinedValueSerde = new JsonSerde<>(JoinedEvent.class, objectMapper);
    }

    @Override
    public void configureBuilder(final StreamsBuilder builder) {

        this.streamDiscriminate(builder);
    }

    private void streamFilter(final StreamsBuilder builder) {

        builder.stream(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Consumed.with(keySerde, consumedValueSerde))
                .filter((key, value) -> value.getSource().equals("TLC")).transform(EventTypeTransformer::new)
                .to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME, Produced.with(keySerde, producedValueSerde));

        log.info("Stream configured");

    }


    private void streamDiscriminate(final StreamsBuilder builder) {

        builder.stream(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Consumed.with(keySerde, consumedValueSerde))
                .map((key, value) -> KeyValue.pair(value.getSource(), value)).transform(EventTypeTransformer::new)
                .to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME, Produced.with(keySerde, producedValueSerde));

        log.info("Stream configured");

    }

    private void streamJoin(final StreamsBuilder builder) {
        final KStream<String, ConsumedEvent> stream =
                builder.stream(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Consumed.with(keySerde, consumedValueSerde)).selectKey((k, v) -> v.getSource());

        final KStream<String, ConsumedEvent> joinStream =
                builder.stream(KafkaConfig.KAFKA_JOIN_TOPIC_NAME, Consumed.with(keySerde, consumedValueSerde)).selectKey((k, v) -> v.getSource());

        stream.join(joinStream, (s, v) -> JoinedEvent.builder().joinedAt(OffsetDateTime.now()).eventLeft(s).eventRight(v).build(),
                        JoinWindows.of(Duration.ofMillis(5000)).grace(Duration.ZERO),
                        StreamJoined.<String, ConsumedEvent, ConsumedEvent>as("joined-event").withKeySerde(keySerde).withValueSerde(consumedValueSerde)
                                .withOtherValueSerde(consumedValueSerde))
                .to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME, Produced.with(keySerde, joinedValueSerde));
    }

    /*private void table(final StreamsBuilder builder) {

        final KTable<Void, ConsumedEvent> table =
                builder.table(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Materialized.with(keySerde, consumedValueSerde));

                table.groupBy((key, value) -> KeyValue.pair(value.getSource(), value.getSource())).count().toStream()
                .filter((key, value) -> Objects.nonNull(value)).to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME);

        log.info("Table configured");
    } */

}
