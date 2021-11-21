package meetup.demo.kafka;

import meetup.demo.config.KafkaConfig;
import meetup.demo.entity.ProducedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaMessagePublisher {

    @Autowired
    private KafkaTemplate<String, ProducedEvent> kafkaTemplate;

    private void sendProducedEvent(final ProducedEvent event) {
       this.kafkaTemplate.send(KafkaConfig.KAFKA_UPSTREAM_OUTBOUND_TOPIC_NAME, null, event);
    }

}
