package com.airtel.cs.model.sr.response.tickethistory;

import com.airtel.cs.model.cs.request.tickethistory.TicketHistoryResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHistoryRequest {
    private String status;
    private Integer statusCode;
    private String message;
    private List<TicketHistoryResult> result;
}
