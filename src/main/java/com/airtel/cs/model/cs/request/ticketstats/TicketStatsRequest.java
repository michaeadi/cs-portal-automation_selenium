package com.airtel.cs.model.cs.request.ticketstats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketStatsRequest {
    private TicketStatsTicketSearchCriteria ticketSearchCriteria;
}