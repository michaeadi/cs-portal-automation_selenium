package com.airtel.cs.model.response.rechargehistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RechargeHistory {

    private String status;
    private int statusCode;
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    private List<ResultRechargeHistory> result;
    private String apiErrors;
    private String startDate;
    private String endDate;
}
