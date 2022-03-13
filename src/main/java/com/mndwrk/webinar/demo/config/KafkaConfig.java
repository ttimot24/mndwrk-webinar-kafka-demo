package com.mndwrk.webinar.demo.config;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import com.mndwrk.webinar.demo.entity.ConsumedEvent;
import com.mndwrk.webinar.demo.kafka.KafkaMeetupDemoEventFilterStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@EnableKafka
@Configuration
public class KafkaConfig {
    public static final String KAFKA_INBOUND_TOPIC_NAME = "webinar-demo-inbound";

    public static final String KAFKA_JOIN_TOPIC_NAME = "webinar-demo-join";

    public static final String KAFKA_OUTBOUND_TOPIC_NAME = "webinar-demo-outbound";

    private final KafkaProperties properties;
    private final ObjectMapper objectMapper;
    private final String kafkaTopicRetention;

    public KafkaConfig(KafkaProperties properties, ObjectMapper objectMapper,
                       @Value(value = "${application.kafka.topic.retention:1000}") String kafkaTopicRetention) {
        this.properties = properties;
        this.objectMapper = objectMapper;
        this.kafkaTopicRetention = kafkaTopicRetention;
    }

    @Bean
    public NewTopic joinMessageTopic() {

        return TopicBuilder.name(KafkaConfig.KAFKA_JOIN_TOPIC_NAME).partitions(10).replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, this.kafkaTopicRetention).build();
    }

    @Bean
    public NewTopic inboundMessageTopic() {

        return TopicBuilder.name(KafkaConfig.KAFKA_INBOUND_TOPIC_NAME).partitions(10).replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, this.kafkaTopicRetention).build();
    }

    @Bean
    public NewTopic outboundMessageTopic() {

        return TopicBuilder.name(KafkaConfig.KAFKA_OUTBOUND_TOPIC_NAME).partitions(10).replicas(1)
                .config(TopicConfig.RETENTION_MS_CONFIG, this.kafkaTopicRetention).build();
    }

    @Bean
    public ConsumerFactory<String, ConsumedEvent> consumerFactory() {
        final JsonDeserializer<ConsumedEvent> deserializer = new JsonDeserializer<>(ConsumedEvent.class, this.objectMapper).trustedPackages("*");

        final ErrorHandlingDeserializer<ConsumedEvent> deserializerWithErrorHandler = new ErrorHandlingDeserializer<>(deserializer);

        return new DefaultKafkaConsumerFactory<>(properties.buildConsumerProperties(), new StringDeserializer(), deserializerWithErrorHandler);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ConsumedEvent> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, ConsumedEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConcurrency(10);
        factory.setConsumerFactory(consumerFactory());
        factory.setRecordFilterStrategy(recordFilterStrategy());

        return factory;
    }


    @Bean
    public RecordFilterStrategy<String, ConsumedEvent> recordFilterStrategy() {
        return new KafkaMeetupDemoEventFilterStrategy();
    }

    @Bean
    public DefaultKafkaProducerFactoryCustomizer producerFactoryCustomizer() {
        return producerFactory -> producerFactory.setValueSerializer(new JsonSerializer<>(this.objectMapper));
    }

}
