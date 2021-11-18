package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSearchRequest {

  public TicketSearchRequest(TicketSearchCriteria ticketSearchCriteria) {
   this.ticketSearchCriteria=ticketSearchCriteria;
  }

  private TicketSearchCriteria ticketSearchCriteria;

  private Integer pageNumber = 0;

  private Integer pageSize = 5;
}
