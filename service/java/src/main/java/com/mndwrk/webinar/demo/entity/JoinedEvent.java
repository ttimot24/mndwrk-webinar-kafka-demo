package com.mndwrk.webinar.demo.entity;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class JoinedEvent {

    @JsonProperty
    OffsetDateTime joinedAt;
    @JsonProperty
    ConsumedEvent eventLeft;
    @JsonProperty
    ConsumedEvent eventRight;

}
