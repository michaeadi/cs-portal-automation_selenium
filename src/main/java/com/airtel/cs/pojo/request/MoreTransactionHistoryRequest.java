package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MoreTransactionHistoryRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
    String daysFilter;
    String startDate;
    String endDate;
    String airtelMoneyTransactionIdSearch;
    String currencyType;
    boolean amHistory;
}
