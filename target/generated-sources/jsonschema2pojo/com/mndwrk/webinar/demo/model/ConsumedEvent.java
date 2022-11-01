
package com.mndwrk.webinar.demo.model;

import java.time.OffsetDateTime;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uuid",
    "source",
    "summary",
    "detectedAt"
})
@Generated("jsonschema2pojo")
public class ConsumedEvent {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    private String uuid;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    private String source;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("summary")
    private String summary;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detectedAt")
    private OffsetDateTime detectedAt;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("summary")
    public String getSummary() {
        return summary;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("summary")
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detectedAt")
    public OffsetDateTime getDetectedAt() {
        return detectedAt;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("detectedAt")
    public void setDetectedAt(OffsetDateTime detectedAt) {
        this.detectedAt = detectedAt;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ConsumedEvent.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("uuid");
        sb.append('=');
        sb.append(((this.uuid == null)?"<null>":this.uuid));
        sb.append(',');
        sb.append("source");
        sb.append('=');
        sb.append(((this.source == null)?"<null>":this.source));
        sb.append(',');
        sb.append("summary");
        sb.append('=');
        sb.append(((this.summary == null)?"<null>":this.summary));
        sb.append(',');
        sb.append("detectedAt");
        sb.append('=');
        sb.append(((this.detectedAt == null)?"<null>":this.detectedAt));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.summary == null)? 0 :this.summary.hashCode()));
        result = ((result* 31)+((this.detectedAt == null)? 0 :this.detectedAt.hashCode()));
        result = ((result* 31)+((this.source == null)? 0 :this.source.hashCode()));
        result = ((result* 31)+((this.uuid == null)? 0 :this.uuid.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof ConsumedEvent) == false) {
            return false;
        }
        ConsumedEvent rhs = ((ConsumedEvent) other);
        return (((((this.summary == rhs.summary)||((this.summary!= null)&&this.summary.equals(rhs.summary)))&&((this.detectedAt == rhs.detectedAt)||((this.detectedAt!= null)&&this.detectedAt.equals(rhs.detectedAt))))&&((this.source == rhs.source)||((this.source!= null)&&this.source.equals(rhs.source))))&&((this.uuid == rhs.uuid)||((this.uuid!= null)&&this.uuid.equals(rhs.uuid))));
    }

}
