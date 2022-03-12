package com.mndwrk.webinar.demo.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ProducedEvent extends Event {

    public ProducedEvent(UUID uuid, String source, String description, OffsetDateTime detectedAt) {
        super(uuid, source, description, detectedAt);
    }

}
