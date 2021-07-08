package com.airtel.cs.pojo.response.loandetails;

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
public class LoanRepaymentList {
    private String id;
    private Double amountCredited;
    private Double serviceCharge;
    private Double recovered;
    private String loanType;
    private String dateCreated;
    private String loanChannel;
    private LoanRepaymentTransaction loanRepaymentTransaction;
}
