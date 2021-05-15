package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
    String type;
    String startDate;
    String endDate;
    String action;
}
