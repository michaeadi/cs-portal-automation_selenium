package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketSearchRequest {

  private TicketSearchCriteria ticketSearchCriteria;
}
