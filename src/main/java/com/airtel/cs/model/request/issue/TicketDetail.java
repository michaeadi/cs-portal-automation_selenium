package com.airtel.cs.model.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDetail {
    private String ticketId;
    private TicketPoolDetail ticketPool;
    private ExternalStates state;
    private Map<String, Long> sla;
    private String expectedClosureDate;
    private PriorityDetail priority;
}
