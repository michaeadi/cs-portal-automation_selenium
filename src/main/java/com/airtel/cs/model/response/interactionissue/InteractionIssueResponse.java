package com.airtel.cs.model.response.interactionissue;

import com.airtel.cs.model.request.openapi.interactionissue.InteractionIssueOpenAPIResponse;
import com.airtel.cs.model.request.tickethistorylog.TicketInteractionComment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionIssueResponse {
    private String message;
    private String status;
    private Integer statusCode;
    private Integer ticketId ;
    private InteractionIssueOpenAPIResponse result;
    private ArrayList<TicketInteractionComment> ticketInteractionComments;
    private InteractionTicketDetail ticket;
}
