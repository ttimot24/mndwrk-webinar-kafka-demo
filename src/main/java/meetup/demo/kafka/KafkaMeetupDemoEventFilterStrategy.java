package meetup.demo.kafka;

import meetup.demo.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMeetupDemoEventFilterStrategy implements RecordFilterStrategy<String, Event> {

    @Override
    public boolean filter(final ConsumerRecord<String, Event> record) {

        return true;
    }

}
