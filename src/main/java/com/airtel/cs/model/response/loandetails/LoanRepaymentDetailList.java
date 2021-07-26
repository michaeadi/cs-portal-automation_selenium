package com.airtel.cs.model.response.loandetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanRepaymentDetailList {
    private String transactionId;
    private Double amountRecovered;
    private String recoveryMethod;
    private String dateRecovered;
}
