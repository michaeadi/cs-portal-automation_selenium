package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_ABSENT)

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
}