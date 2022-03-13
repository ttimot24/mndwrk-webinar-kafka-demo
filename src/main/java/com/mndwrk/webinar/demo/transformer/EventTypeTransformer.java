package com.mndwrk.webinar.demo.transformer;

import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import com.mndwrk.webinar.demo.entity.ProducedEvent;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Transformer;
import org.apache.kafka.streams.processor.ProcessorContext;

public class EventTypeTransformer implements Transformer<String, ConsumedEvent, KeyValue<String, ProducedEvent>> {

    @Override
    public void init(ProcessorContext processorContext) {

    }

    @Override
    public KeyValue<String, ProducedEvent> transform(String key, ConsumedEvent value) {
        return KeyValue.pair(key, ProducedEvent.builder().uuid(value.getUuid()).source(value.getSource()).description(value.getDescription())
                .detectedAt(value.getDetectedAt()).build());
    }

    @Override
    public void close() {

    }
}
