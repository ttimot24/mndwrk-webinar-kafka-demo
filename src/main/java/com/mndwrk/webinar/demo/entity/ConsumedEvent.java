package com.mndwrk.webinar.demo.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class ConsumedEvent extends Event{

    public ConsumedEvent(UUID uuid, String source, String description, OffsetDateTime detectedAt) {
        super(uuid, source, description, detectedAt);
    }

}
