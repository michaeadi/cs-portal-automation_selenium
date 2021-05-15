package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
    String startDate;
    String endDate;
}
