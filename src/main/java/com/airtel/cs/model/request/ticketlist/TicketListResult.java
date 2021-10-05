package com.airtel.cs.model.request.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketListResult implements Serializable {
    private String ticketId;
    private String sourceApp;
   /* private String createdOn;
    private String workGroup;
    private String agentAuuid;
    private String agentName;
    private String workgroupSla;
    private PriorityDetail priority;
    private ExternalStates state;
    private CreatedBy createdBy;
    private ArrayList<EscalationLevelDetails> escalationLevelDetails;
    private TicketPoolDetail ticketPool;*/
}
