package com.airtel.cs.model.response.postpaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statement {
    public String status;
    public String accountNo;
    public String transactionAmountDebit;
    public String transactionAmountCredit;
    public String transactionDate;
    public String transactionStatus;
    public String transactionNo;
    public String transactionType;
    public String accountName;
    public String accountBalance;
    public String currency;
}
