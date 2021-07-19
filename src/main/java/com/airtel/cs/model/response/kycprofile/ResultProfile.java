package com.airtel.cs.model.response.kycprofile;

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
public class ResultProfile {

    private String deviceType;
    private String imeiNumber;
    private String type;
    private String brand;
    private String model;
    private String os;
    private String airtelMoneyStatus;
    private String serviceStatus;
}