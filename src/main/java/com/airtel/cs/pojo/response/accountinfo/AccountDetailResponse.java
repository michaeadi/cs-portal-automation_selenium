package com.airtel.cs.pojo.response.accountinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDetailResponse {

  private String dateTime;
  private String transactionType;
  private String status;
  private String referenceNo;
  private String referenceId;
  private String billAmount;
  private String amntReceived;
}
