package com.airtel.cs.model.request.ticketstats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketStatsRequest {
    private TicketStatsTicketSearchCriteria ticketSearchCriteria;
}