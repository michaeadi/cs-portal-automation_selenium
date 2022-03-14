package com.airtel.cs.model.cs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class InteractionHistoryRequest {
  private Map<String, String> clientInfo;
  private Integer pageNumber;
  private Integer pageSize;
}
