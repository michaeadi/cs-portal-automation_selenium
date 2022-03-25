package com.airtel.cs.model.cs.request.psb.cs;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class FetchBalanceRequest {
    private String msisdn;
    private String idNumber;
    private String idType;
}
