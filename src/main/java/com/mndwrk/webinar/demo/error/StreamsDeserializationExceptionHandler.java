package com.mndwrk.webinar.demo.error;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.processor.ProcessorContext;

@Slf4j
public class StreamsDeserializationExceptionHandler implements DeserializationExceptionHandler {

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public DeserializationHandlerResponse handle(ProcessorContext processorContext, ConsumerRecord<byte[], byte[]> consumerRecord, Exception e) {
        log.error("Could not deserialize message...");
        return DeserializationHandlerResponse.CONTINUE;
    }
}
