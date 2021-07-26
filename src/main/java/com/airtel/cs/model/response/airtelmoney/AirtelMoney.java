package com.airtel.cs.model.response.airtelmoney;

import com.airtel.cs.model.response.vendors.ApiErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirtelMoney {
    private String message;
    private String status;
    private Integer statusCode;
    private ApiErrors apiErrors;
    private AirtelMoneyResult result;
}
