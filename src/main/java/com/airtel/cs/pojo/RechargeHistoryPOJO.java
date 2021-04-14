package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeHistoryPOJO {

    String status;
    int statusCode;
    int pageSize;
    int pageNumber;
    int totalCount;
    ArrayList<ResultRechargeHistoryPOJO> result;
    apiErrors apiErrors;
    String startDate;
    String endDate;
}