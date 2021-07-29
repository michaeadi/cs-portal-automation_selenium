package com.airtel.cs.model.response.transfertoqueue;

import com.airtel.cs.model.response.ticketlist.TicketPool;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferToQueue {
    private String statusCode;
    private String status;
    private String message;
    private List<TicketPool> result;
}
