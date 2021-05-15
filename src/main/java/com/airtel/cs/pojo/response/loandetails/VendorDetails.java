package com.airtel.cs.pojo.response.loandetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorDetails {
    private String vendorName;
    private String vendorDisplayName;
    private LoanDetails loanDetails;
    private LoanHistory loanHistory;
}
