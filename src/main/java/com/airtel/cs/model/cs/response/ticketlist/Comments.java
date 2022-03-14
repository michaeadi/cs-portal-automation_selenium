package com.airtel.cs.model.cs.response.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comments {
    private String agentId;

    private String interactionId;

    private TicketPool ticketPool;

    private String commentType;

    private String agentName;

    private String comment;

    private String id;

    private String updatedOn;

    private String createdOn;
}
