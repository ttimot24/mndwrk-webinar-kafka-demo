package com.mndwrk.webinar.demo.config;

import com.mndwrk.webinar.demo.error.StreamsDeserializationExceptionHandler;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
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
            factoryBean.getStreamsConfiguration().put(StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, StreamsDeserializationExceptionHandler.class);
        };
    }

}
