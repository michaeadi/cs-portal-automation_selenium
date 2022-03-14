package com.airtel.cs.model.cs.request.ticketreopen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReopenResult {
    private ArrayList<ReopenTicketList> reopenedTicketList;
    private Integer ticketsReOpened;
}

