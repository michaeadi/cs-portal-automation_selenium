package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
    private String type;
    private String startDate;
    private String endDate;
    private String action;
}
