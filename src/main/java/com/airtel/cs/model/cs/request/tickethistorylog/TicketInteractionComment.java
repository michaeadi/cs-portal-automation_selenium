package com.airtel.cs.model.cs.request.tickethistorylog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketInteractionComment {
    private String type;
    private String createdOn;
    private String agentName;
    private String ticketId;
    private String interactionId;
    private Boolean workgroupSlaBreached;
    private Boolean customerSlaBreached;
    private Boolean escalated;
    private String typeDetails;
    private String assignTo;
}
