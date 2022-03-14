package com.airtel.cs.model.cs.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetails {
    public String amount;
    public String totalOutstanding;
    public String totalUnbilled;
    public String currencyType;
    public Long dueDate;
}
