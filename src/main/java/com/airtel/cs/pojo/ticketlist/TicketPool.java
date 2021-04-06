package com.airtel.cs.pojo.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketPool {
    private String ticketPoolName;

    private boolean active;

    private int id;

    private ArrayList<ExternalStates> externalStates;
}
