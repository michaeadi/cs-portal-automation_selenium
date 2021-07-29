package com.airtel.cs.model.response.loandetails;

import com.airtel.cs.model.response.vendors.ApiErrors;
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
public class Loan {
    private String status;
    private String statusCode;
    private VendorDetails result;
    private ApiErrors apiErrors;
    private String message;
}
