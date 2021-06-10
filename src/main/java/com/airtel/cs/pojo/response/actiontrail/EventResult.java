package com.airtel.cs.pojo.response.actiontrail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventResult {
    private String actionType;
    private String createdOn;
    private String reason;
    private String agentId;
    private String agentName;
    private String comments;
}
