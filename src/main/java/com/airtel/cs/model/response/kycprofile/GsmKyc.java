package com.airtel.cs.model.response.kycprofile;

import com.airtel.cs.model.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GsmKyc {
    private String status;
    private Integer statusCode;
    private ResultGsmKyc result;
    private APIErrors apiErrors;
}
