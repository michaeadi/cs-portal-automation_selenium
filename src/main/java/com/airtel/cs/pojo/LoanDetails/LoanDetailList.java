package com.airtel.cs.pojo.LoanDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanDetailList {
    private Double totalLoanEligibility;
    private Integer countOfEvents;
    private Double totalLoanAmount;
    private Double loanPaid;
    private Double remainingBalance;
    private String loanId;
    private String dueDate;
}
