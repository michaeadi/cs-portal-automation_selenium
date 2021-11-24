package com.airtel.cs.model.request.interactionissue;

import com.airtel.cs.model.request.issue.PriorityDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionTicketDetail {
    private String ticketId;
    private String issueId;
    private String expectedClosureDate;
    private PriorityDetail priority;
    private String sla;
    private String agentId;
    private Boolean assigned;
    private Boolean reopen;
    private Boolean workgroupSlaBreached;
    private Boolean customerSlaBreached;
}