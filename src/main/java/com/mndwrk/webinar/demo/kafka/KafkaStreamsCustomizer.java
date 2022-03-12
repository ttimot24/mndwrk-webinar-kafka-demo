package com.mndwrk.webinar.demo.kafka;

import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mndwrk.webinar.demo.config.KafkaConfig;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaStreamsCustomizer implements KafkaStreamsInfrastructureCustomizer {

    final Serde<String> keySerde;
    final Serde<ConsumedEvent> consumedValueSerde;
    final Serde<ConsumedEvent> producedValueSerde;

    public KafkaStreamsCustomizer(final ObjectMapper objectMapper) {
        log.info("-------------------------------------------------------------------------------");
        keySerde = Serdes.String();
        consumedValueSerde = new JsonSerde<>(ConsumedEvent.class, objectMapper);
        producedValueSerde = new JsonSerde<>(ConsumedEvent.class, objectMapper);
    }

    @Override
    public void configureBuilder(final StreamsBuilder builder) {

        final KStream<String, ConsumedEvent> stream =
                builder.stream(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Consumed.with(keySerde, consumedValueSerde))
                .filter((key, value) -> value.getSource().equals("TLC"))
                .map((key, value) -> KeyValue.pair(key, value))
                .filter((key, value) -> Objects.nonNull(value));

        stream.to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME, Produced.with(keySerde, producedValueSerde));

        log.info("Stream configured");
    }

    @Override
    public void configureTopology(final Topology topology){
        log.info("Topology configured");
    }


    public void table(final StreamsBuilder builder) {

        final KTable<String, ConsumedEvent> table = builder.table(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME, Materialized.with(keySerde, consumedValueSerde));
        table.groupBy((key, value) -> KeyValue.pair(value.getSource(), value.getSource())).count().toStream().filter((key, value) -> Objects.nonNull(value)).to(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME);

        log.info("Message sent");
    }

}
