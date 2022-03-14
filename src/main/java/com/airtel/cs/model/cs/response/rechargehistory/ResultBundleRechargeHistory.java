package com.airtel.cs.model.cs.response.rechargehistory;

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
public class ResultBundleRechargeHistory {
    private String packageCategory;
    private String bundleName;
    private String bundlePrice;
    private String expiresOn;
    private String status;
    private String subscriptionDateTime;
    private String txnNumber;
    private String validity;
}
