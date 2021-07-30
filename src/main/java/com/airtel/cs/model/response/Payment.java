package com.airtel.cs.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Payment {
    public String accountNo;
    public String invoiceNo;
    public String paymentAmount;
    public String paymentStatus;
    public String currency;
    public String paymentDate;
    public String paymentAllocationDate;
    public String paymentMode;
    public String transactionNo;
}
