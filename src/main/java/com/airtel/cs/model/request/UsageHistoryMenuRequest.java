package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryMenuRequest {
    private String msisdn;
    private int pageSize;
    private int pageNumber;
    private String typeFilter;
    private String startDate;
    private String endDate;
    private String action;
    private String cdrTypeFilter;
}
