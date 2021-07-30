package com.airtel.cs.model.response.actiontrail;

import com.airtel.cs.model.response.actionconfig.MetaInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventResult {
    private String actionType;
    private String createdOn;
    private String reason;
    private String agentId;
    private String agentName;
    private String comments;
    private List<MetaInfo> metaInfo;
}
