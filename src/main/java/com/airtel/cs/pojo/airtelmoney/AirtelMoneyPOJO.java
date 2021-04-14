package com.airtel.cs.pojo.airtelmoney;

import com.airtel.cs.pojo.vendors.ApiErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirtelMoneyPOJO {
    private String message;
    private String status;
    private Integer statusCode;
    private ApiErrors apiErrors;
    private AirtelMoneyResult result;
}