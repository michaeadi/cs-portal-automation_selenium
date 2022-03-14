package com.airtel.cs.model.cs.response.postpaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    public String dateTime;
    public String transactionType;
    public String status;
    public String referenceNo;
    public String referenceId;
    public String billAmount;
    public String amntReceived;
}
