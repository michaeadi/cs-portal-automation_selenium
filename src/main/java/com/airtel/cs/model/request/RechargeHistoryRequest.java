package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RechargeHistoryRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
    private String startDate;
    private String endDate;
    private String rechargeHistoryVoucherSearch;
}
