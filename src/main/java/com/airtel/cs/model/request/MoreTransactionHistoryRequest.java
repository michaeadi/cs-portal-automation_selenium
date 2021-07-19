package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoreTransactionHistoryRequest {
    private String msisdn;
    private int pageSize;
    private int pageNumber;
    private String daysFilter;
    private String startDate;
    private String endDate;
    private String airtelMoneyTransactionIdSearch;
    private String currencyType;
    private boolean amHistory;
}
