package com.airtel.cs.model.cs.request.psb.cs;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class BankDetailsRequest {
    private String msisdn;
    private String nubanId;
}
