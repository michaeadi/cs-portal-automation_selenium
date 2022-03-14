package com.airtel.cs.model.cs.response.vas;
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
public class Result {
    private String vasStatus;
    private String vasId;
    private String vasName;
    private String subVendorId;
    private Long vasStartDate;
    private Long vasEndDate;
    private Long lastChargedDate;
    private Long  nextRenewDate;
    private Long vasSubscriptionDate;
    private Integer activationAmount;
    private String activationCurrency;
    private String activationAmountWithCurrency;
    private String subscriptionAmount;
    private String subscriptionCurrency;
    private String subscriptionAmountWithCurrency;
    private Integer validity;
    private String validityUnit;
    private String validityWithUnit;
    private String language;
    private String subscriptionChannel;
    private Boolean autoRenewable;
    private String autoRenewMode;
    private String vasBenefit;
    private String activationRequestSMS;
    private String activationApp;
    private String vendor;
    private String customerStatus;
    private String lastChargedAmountWithCurrency;
}
