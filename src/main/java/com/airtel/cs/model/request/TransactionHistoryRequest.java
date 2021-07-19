package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionHistoryRequest {
    private String msisdn;
    private int pageSize;
    private int pageNumber;
    private String startDate;
    private String endDate;
}
