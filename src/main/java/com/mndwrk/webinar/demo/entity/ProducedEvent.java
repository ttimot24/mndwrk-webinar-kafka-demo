package com.mndwrk.webinar.demo.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProducedEvent extends Event {

    @Builder
    public ProducedEvent(UUID uuid, String source, String description, OffsetDateTime detectedAt) {
        super(uuid, source, description, detectedAt);
    }

}
