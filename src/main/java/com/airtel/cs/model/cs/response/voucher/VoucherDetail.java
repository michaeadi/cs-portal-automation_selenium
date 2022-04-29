package com.airtel.cs.model.cs.response.voucher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoucherDetail {
    private String voucherId;
    private String status;
    private String subStatus;
    private String rechargeAmount;
    private String expiryDate;
    private String batchId;
    private String agent;
    private String voucherGroup;
    private String timestamp;
    private String subscriberId;
    private String activationCode;
    private String message;
}
