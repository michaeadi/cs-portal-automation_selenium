package com.airtel.cs.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentHistory {
    public String billCycle;
    public String billNumber;
    public long billStartDate;
    public long billEndDate;
    public long invoiceDate;
    public long invoiceDueDate;
    public String currency;
    public String totalOutstanding;
    public String adjustments;
    public String currentInvoice;
    public String previousBalance;
    public String previousPaymentDone;
}
