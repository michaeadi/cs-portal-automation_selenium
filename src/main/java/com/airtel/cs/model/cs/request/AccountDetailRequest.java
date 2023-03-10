package com.airtel.cs.model.cs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailRequest {
  private String accountNo;
  private String pageNumber;
  private String pageSize;
}
