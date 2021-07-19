package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryRequest {
    private String msisdn;
    private int pageSize;
    private int pageNumber;
    private String type;
    private String startDate;
    private String endDate;
    private String action;
}
