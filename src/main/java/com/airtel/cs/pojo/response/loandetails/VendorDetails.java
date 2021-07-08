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
public class VendorDetails {
    private String vendorName;
    private String vendorDisplayName;
    private LoanDetails loanDetails;
    private LoanHistory loanHistory;
}
