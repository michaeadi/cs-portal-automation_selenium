package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultRechargeHistoryPOJO {
    String charges;
    String dateTime;
    String bundleName;
    String transactionId;
    String status;
    String packageCategory;
    rechargeBenefitPOJO rechargeBenefit;
    String mode;
    String validity;
    String expiryDate;
    String serialNumber;
    APIErrors apiErrors;
}