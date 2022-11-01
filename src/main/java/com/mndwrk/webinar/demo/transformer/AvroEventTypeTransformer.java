package com.mndwrk.webinar.demo.transformer;

import com.mndwrk.webinar.demo.entity.ProducedEvent;
import io.confluent.ksql.avro_schemas.KsqlDataSourceSchema;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.OffsetDateTime;
import java.util.UUID;

public class AvroEventTypeTransformer implements Transformer<String, KsqlDataSourceSchema, KeyValue<String, ProducedEvent>> {

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public KeyValue<String, ProducedEvent> transform(String key, KsqlDataSourceSchema value) {
        return KeyValue.pair(key, ProducedEvent.builder().uuid(UUID.fromString(value.getUuid().toString())).source(value.getSource().toString()).summary(value.getSummary().toString())
                .detectedAt(OffsetDateTime.parse(value.getDetectedAt())).build());
    }

    @Override
    public void close() {

    }
}
