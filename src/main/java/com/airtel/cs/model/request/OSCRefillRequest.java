package com.airtel.cs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OSCRefillRequest {

  private String voucherSerialNumber;
  private String msisdn;
  private String damagedPin;
  private Boolean isDamagedPinAvailable;

}
