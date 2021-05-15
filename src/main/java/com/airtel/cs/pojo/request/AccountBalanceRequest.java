package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountBalanceRequest {
    String msisdn;
    int pageSize;
    int pageNumber;
}
