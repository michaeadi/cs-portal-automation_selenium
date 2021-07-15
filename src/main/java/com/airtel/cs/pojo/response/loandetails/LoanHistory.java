package com.airtel.cs.pojo.response.loandetails;

import com.airtel.cs.pojo.response.vendors.HeaderList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanHistory {
    private int repaymentsCount;
    private ArrayList<HeaderList> headerList;
    private ArrayList<LoanRepaymentList> loanRepaymentList;
}
