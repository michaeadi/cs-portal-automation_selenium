package com.airtel.cs.pojo.response;

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
public class ResultBundleRechargeHistoryPOJO {
    String packageCategory;
    String bundleName;
    String bundlePrice;
    String expiresOn;
    String status;
    String subscriptionDateTime;
    String txnNumber;
    String validity;
}
