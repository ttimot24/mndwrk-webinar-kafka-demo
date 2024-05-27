package com.mndwrk.webinar.demo.transformer;

import com.mndwrk.webinar.demo.entity.ProducedEvent;
import com.mndwrk.webinar.demo.ksqldb.AvroConsumedEvent;
import com.mndwrk.webinar.demo.ksqldb.AvroProducedEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

public class AvroEventTypeTransformer implements Transformer<String, AvroConsumedEvent, KeyValue<String, AvroProducedEvent>> {

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public KeyValue<String, AvroProducedEvent> transform(String key, AvroConsumedEvent value) {
        return KeyValue.pair(key,
                AvroProducedEvent.newBuilder()
                                 .setUuid(value.getUuid())
                                 .setSource(value.getSource().toString())
                                 .setSummary(value.getSummary().toString())
                                 .setDetectedAt(value.getDetectedAt())
                            .build());
    }

    @Override
    public void close() {

    }
}
