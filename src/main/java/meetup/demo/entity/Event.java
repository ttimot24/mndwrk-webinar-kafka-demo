package meetup.demo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonSubTypes({
   @JsonSubTypes.Type(value = ConsumedEvent.class),
   @JsonSubTypes.Type(value = ProducedEvent.class),
})

public class Event {

    private String source;
    private String description;

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
        return "Event{" + "name='" + source + '\'' + ", description='" + description + '\'' + '}';
    }

}
