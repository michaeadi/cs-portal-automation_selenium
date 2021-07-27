package com.airtel.cs.model.response.apierror;

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
public class APIErrors {
    private String lastRecharge;
    private String mainAccountBalance;
    private String appStatus;
    private String airtelMoneyStatus;
    private String sim;
    private String segment;
    private String subSegment;
    private String lineType;
    private String puk;
    private String activationDate;
    private String serviceCategory;
    private String status;
}
