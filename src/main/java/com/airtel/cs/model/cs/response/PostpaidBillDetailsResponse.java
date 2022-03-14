package com.airtel.cs.model.cs.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostpaidBillDetailsResponse {
    public String customerName;
    public String lastPaidBill;
    public String lastPaidBillDate;
    public InvoiceDetails invoiceDetails;
    public String currencyType;
    public String lastBillDate;
}
