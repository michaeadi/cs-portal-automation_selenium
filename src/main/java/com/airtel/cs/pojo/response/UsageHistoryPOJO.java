package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsageHistoryPOJO {

    String status;
    int statusCode;
    int pageSize;
    int pageNumber;
    int totalCount;
    ArrayList<ResultUsageHistoryPOJO> result;
    APIErrors apiErrors;
    String startDate;
    String endDate;
}
