package com.airtel.cs.model.cs.request.ticketdetail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketPool {
    private String queueName;
    private boolean active;

    private int id;

    private ArrayList<ExternalStates> externalStates;
}
