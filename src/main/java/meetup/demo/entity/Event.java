package meetup.demo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import java.util.UUID;

@JsonSubTypes({
   @JsonSubTypes.Type(value = ConsumedEvent.class),
   @JsonSubTypes.Type(value = ProducedEvent.class),
})

public class Event {

    private UUID uuid;
    private String source;
    private String description;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Event{" + "uuid=" + uuid + ", source='" + source + '\'' + ", description='" + description + '\'' + '}';
    }
}
