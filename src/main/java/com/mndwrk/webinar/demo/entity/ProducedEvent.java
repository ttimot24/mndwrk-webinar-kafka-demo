package com.mndwrk.webinar.demo.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProducedEvent extends Event {

    private String processedBy;
    @Builder
    public ProducedEvent(UUID uuid, String source, String summary, String processedBy, long detectedAt) {
        super(uuid, source, summary, detectedAt);
        this.processedBy = processedBy;
    }

    public String getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(String processedBy) {
        this.processedBy = processedBy;
    }
}
