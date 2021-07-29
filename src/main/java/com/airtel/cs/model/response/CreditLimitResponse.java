package com.airtel.cs.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditLimitResponse {
    public String status;
    public String message;
    public String availableCreditLimit;
    public String totalCreditLimit;
    public String tempCreditLimit;
    public String currency;
}
