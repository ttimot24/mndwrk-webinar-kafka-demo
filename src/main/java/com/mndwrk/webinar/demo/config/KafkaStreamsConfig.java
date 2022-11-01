package com.mndwrk.webinar.demo.config;

import com.mndwrk.webinar.demo.error.StreamsDeserializationExceptionHandler;
import com.mndwrk.webinar.demo.ksqldb.AvroConsumedEvent;
import io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

import java.util.Collections;
import java.util.Map;

@Profile("kafka-streams")
@EnableKafkaStreams
@Configuration
public class KafkaStreamsConfig {

    //https://spring.io/blog/2019/12/06/stream-processing-with-spring-cloud-stream-and-apache-kafka-streams-part-5-application-customizations

    @Bean
    public StreamsBuilderFactoryBeanConfigurer infrastructureCustomizer(KafkaStreamsInfrastructureCustomizer infrastructureCustomizer) {
        return factoryBean -> {
            factoryBean.setInfrastructureCustomizer(infrastructureCustomizer);
            //Built in: LogAndContinue
            factoryBean.getStreamsConfiguration()
                    .putAll(Map.of(
                       KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true,
                       StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsDeserializationExceptionHandler.class
                    ));
        };
    }

    @Bean
    public SpecificAvroSerde<AvroConsumedEvent> getSchemaAwareSerDe(@Value("${spring.kafka.properties.schema.registry.url}") String schemaRegistryUrl){

        final Map<String, Object> serdeConfig = Collections
                .singletonMap(AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

        final SpecificAvroSerde specificAvroSerde = new SpecificAvroSerde<>();
        specificAvroSerde.configure(serdeConfig, false);

        return specificAvroSerde;
    }

}
