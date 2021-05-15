package com.airtel.cs.pojo.response.loandetails;

import com.airtel.cs.pojo.response.vendors.HeaderList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanRepaymentTransaction {
    private int repaymentTransactionCount;
    private String widgetHeader;
    private ArrayList<HeaderList> headerList;
    private ArrayList<LoanRepaymentDetailList> loanRepaymentDetailList;
}
