package com.airtel.cs.pojo.response.airtelmoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtelMoneyData {
    private String transactionId;
    private String transactionDate;
    private String service;
    private String source;
    private String msisdn;
    private String amount;
    private String serviceCharge;
    private String balanceBefore;
    private String balanceAfter;
    private String status;
    private String currencyType;
    private String txnType;
    private Boolean enableResendSms;
    private String secondPartyName;
}
