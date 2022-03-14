package com.airtel.cs.model.cs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryMenuRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
    private String typeFilter;
    private String startDate;
    private String endDate;
    private String action;
    private String cdrTypeFilter;
}
