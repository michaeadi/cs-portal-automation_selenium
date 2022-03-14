package com.airtel.cs.model.cs.response.hlrservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLROrderHistoryResult implements Serializable {

  private static final long serialVersionUID = 5936704901757531219L;

  private Long eventTime;
  private String taskName;
  private String actionType;
  private String status;
  private String failureReason;
}
