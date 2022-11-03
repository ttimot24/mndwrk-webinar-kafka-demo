package com.mndwrk.webinar.demo.entity;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConsumedEvent extends Event {

    @Builder
    public ConsumedEvent(UUID uuid, String source, String summary, long detectedAt) {
        super(uuid, source, summary, detectedAt);
    }

}
