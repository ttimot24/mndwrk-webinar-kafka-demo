package com.mndwrk.webinar.demo.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@JsonSubTypes({@JsonSubTypes.Type(value = ConsumedEvent.class), @JsonSubTypes.Type(value = ProducedEvent.class),})
@NoArgsConstructor
@AllArgsConstructor
public abstract class Event {

    private UUID uuid;
    private String source;
    private String description;
    private OffsetDateTime detectedAt;

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

    public OffsetDateTime getDetectedAt() {
        return detectedAt;
    }

    public void setDetectedAt(OffsetDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    @Override
    public String toString() {
        return "Event{" + "uuid=" + uuid + ", source='" + source + '\'' + ", description='" + description + '\'' + ", detectedAt=" + detectedAt + '}';
    }
}
