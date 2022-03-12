package com.mndwrk.webinar.demo.config;

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


    @Bean
    public StreamsBuilderFactoryBeanConfigurer myStreamsBuilderFactoryBeanCustomizer(KafkaStreamsInfrastructureCustomizer infrastructureCustomizer) {
        return factoryBean -> factoryBean.setInfrastructureCustomizer(infrastructureCustomizer);
    }

}
