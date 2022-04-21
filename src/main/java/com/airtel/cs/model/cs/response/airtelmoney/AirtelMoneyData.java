package com.airtel.cs.model.cs.response.airtelmoney;

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
    private SourceAccount sourceAccount;
    private DestinationAccount destinationAccount;
    private String txnChannel;
    private String transactionType;
    private String paymentMode;
    private String categoryCode;
    private Boolean isReversal;
    private String gradeCode;
    private String externalTransactionId;
    private String instrumentTransactionId;

}
