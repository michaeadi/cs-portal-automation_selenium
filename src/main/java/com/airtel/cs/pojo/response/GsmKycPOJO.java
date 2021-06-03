package com.airtel.cs.pojo.response;

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
    APIErrors apiErrors;
}
