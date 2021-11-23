package meetup.demo.kafka;

import meetup.demo.entity.ConsumedEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class KafkaMeetupDemoEventFilterStrategy implements RecordFilterStrategy<String, ConsumedEvent> {

    private static final List<String> FILTER_OUT = List.of("CCTV");

    @Override
    public boolean filter(final ConsumerRecord<String, ConsumedEvent> record) {

        boolean filtered = FILTER_OUT.contains(record.value().getSource());

        if(filtered) {
            log.info("Event filtered out by source: {}", record.value().getSource());
        }
        return filtered;
    }

}
