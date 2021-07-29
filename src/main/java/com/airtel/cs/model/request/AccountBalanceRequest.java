package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountBalanceRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
}
