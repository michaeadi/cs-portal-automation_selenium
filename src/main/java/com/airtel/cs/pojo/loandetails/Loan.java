package com.airtel.cs.pojo.loandetails;

import com.airtel.cs.pojo.vendors.ApiErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {
    private String status;
    private String statusCode;
    private VendorDetails result;
    private ApiErrors apiErrors;
    private String message;
}
