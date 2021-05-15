package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RechargeHistoryRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
    String startDate;
    String endDate;
    String rechargeHistoryVoucherSearch;
}
