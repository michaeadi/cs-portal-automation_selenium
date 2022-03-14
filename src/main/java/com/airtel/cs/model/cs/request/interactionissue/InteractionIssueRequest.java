package com.airtel.cs.model.cs.request.interactionissue;

import com.airtel.cs.model.cs.request.openapi.interactionissue.InteractionIssueResponse;
import com.airtel.cs.model.cs.request.tickethistorylog.TicketInteractionComment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionIssueRequest {
    private String message;
    private String status;
    private Integer statusCode;
    private Integer ticketId ;
    private InteractionIssueResponse result;
    private ArrayList<TicketInteractionComment> ticketInteractionComments;
    private InteractionTicketDetail ticket;
}