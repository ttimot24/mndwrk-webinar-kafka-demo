package com.mndwrk.webinar.demo.config;

import com.mndwrk.webinar.demo.error.StreamsDeserializationExceptionHandler;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.streams.serdes.avro.ReflectionAvroSerde;
import io.confluent.ksql.avro_schemas.KsqlDataSourceSchema;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;
import org.springframework.kafka.config.StreamsBuilderFactoryBeanConfigurer;

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
                    .put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsDeserializationExceptionHandler.class);
        };
    }

    @Bean
    public Serde<KsqlDataSourceSchema> getSchemaAwareSerDe(@Value("${spring.kafka.properties.schema.registry.url}") String schemaRegistryUrl){

        final SchemaRegistryClient schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl,10);

        return new ReflectionAvroSerde(schemaRegistryClient, KsqlDataSourceSchema.class);
    }

}
