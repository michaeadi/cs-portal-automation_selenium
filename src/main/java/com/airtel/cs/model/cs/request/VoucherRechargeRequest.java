package com.airtel.cs.model.cs.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherRechargeRequest {

  private String voucherNumber;
  private String msisdn;
  private String damagedPin;
  private String key;
}
