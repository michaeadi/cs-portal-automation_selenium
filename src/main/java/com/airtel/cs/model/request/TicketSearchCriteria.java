package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class TicketSearchCriteria {

  private Map<String, String> clientInfo;
}
