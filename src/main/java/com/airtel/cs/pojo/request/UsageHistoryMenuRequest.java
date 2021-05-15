package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsageHistoryMenuRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
    String typeFilter;
    String startDate;
    String endDate;
    String action;
    String cdrTypeFilter;
}
