package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FetchTicketPoolRequest {
    private List<String> ticketId;
    private Boolean isSupervisor;
}
