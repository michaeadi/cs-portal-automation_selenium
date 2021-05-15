package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class apiErrors {
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
