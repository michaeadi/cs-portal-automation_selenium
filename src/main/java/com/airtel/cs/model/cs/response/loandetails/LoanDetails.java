package com.airtel.cs.model.cs.response.loandetails;

import com.airtel.cs.model.cs.response.vendors.HeaderList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanDetails {
    private List<HeaderList> headerList;
    private List<LoanDetailList> loanDetailList;
}
