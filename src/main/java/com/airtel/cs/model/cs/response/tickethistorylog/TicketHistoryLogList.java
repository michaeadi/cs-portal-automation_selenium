package com.airtel.cs.model.cs.response.tickethistorylog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHistoryLogList {
    private String type;
    private String createdOn;
    private String agentName;
    private String ticketId;
    private String interactionId;
    private String workgroupSlaBreached;
    private String customerSlaBreached;
    private String escalated;
    private String bulkUpdated;
    private String event;
    private String state;
    private String assignTo;
    private Integer agentId;
}
