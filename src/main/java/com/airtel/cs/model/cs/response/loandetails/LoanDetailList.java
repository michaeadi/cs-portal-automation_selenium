package com.airtel.cs.model.cs.response.loandetails;

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
public class LoanDetailList {
    private Double totalLoanEligibility;
    private Integer countOfEvents;
    private String totalLoanAmount;
    private String loanPaid;
    private String remainingBalance;
    private String loanId;
    private String dueDate;
}
