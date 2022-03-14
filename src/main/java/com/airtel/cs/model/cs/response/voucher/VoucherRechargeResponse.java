package com.airtel.cs.model.cs.response.voucher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherRechargeResponse {

  private String status;
  private Integer statusCode;
  private VoucherResponse result;
  private String message;
  private Map<String, String> apiErrors;
}
