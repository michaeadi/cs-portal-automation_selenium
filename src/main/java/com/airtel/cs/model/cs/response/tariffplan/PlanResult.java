package com.airtel.cs.model.cs.response.tariffplan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanResult {
    private String planName;
    private String planCode;
    private String serviceClass;
    private String planDescription;
    private Integer planId;
    private String currency;
    private String amount;

}
