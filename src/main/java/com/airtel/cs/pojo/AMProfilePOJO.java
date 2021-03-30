package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AMProfilePOJO {

    String message;
    int statusCode;
    ResultAMProfilePOJO result;
    apiErrors apiErrors;
}
