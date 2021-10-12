package com.airtel.cs.model.request.ticketstats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketStatsRequest {
    private String opco;
    private String key;
    private String value;
}