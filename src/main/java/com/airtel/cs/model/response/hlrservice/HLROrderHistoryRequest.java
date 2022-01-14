package com.airtel.cs.model.response.hlrservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLROrderHistoryRequest implements Serializable {

  private static final long serialVersionUID = 2735135499368382126L;

  private String msisdn;
  private String startDate;
  private String endDate;
  private Integer pageSize;
  private Integer pageNumber;
}

