package com.airtel.cs.pojo.response.transfertoqueue;

import com.airtel.cs.pojo.response.ticketlist.TicketPool;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferToQueuePOJO {
    private String statusCode;
    private String status;
    private String message;
    private ArrayList<TicketPool> result;
}
