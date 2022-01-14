package com.airtel.cs.model.response.hlrservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLROrderHistoryResponse implements Serializable {

  private static final long serialVersionUID = 9116513036462197841L;

  private String status;
  private Integer statusCode;
  private Integer pageNumber;
  private Integer pageSize;
  private Integer totalCount;
  private String startDate;
  private String endDate;
  private List<HLROrderHistoryResult> result;
}
