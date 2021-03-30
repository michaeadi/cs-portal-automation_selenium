package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GsmKycPOJO {
    String status;
    int statusCode;
    ResultGsmKycPOJO result;
    apiErrors apiErrors;
}
