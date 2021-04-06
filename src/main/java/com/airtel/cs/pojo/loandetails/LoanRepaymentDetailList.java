package com.airtel.cs.pojo.loandetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanRepaymentDetailList {
    private String transactionId;
    private Double amountRecovered;
    private String recoveryMethod;
    private String dateRecovered;
}
