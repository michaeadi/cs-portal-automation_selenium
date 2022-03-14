package com.airtel.cs.model.cs.response.rechargehistory;

import com.airtel.cs.model.cs.response.apierror.APIErrors;
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
public class ResultRechargeHistory {
    private String charges;
    private String dateTime;
    private String bundleName;
    private String transactionId;
    private String status;
    private String packageCategory;
    private RechargeBenefit rechargeBenefit;
    private String mode;
    private String validity;
    private String expiryDate;
    private String oldExpiryDate;
    private String serialNumber;
    private APIErrors apiErrors;
}