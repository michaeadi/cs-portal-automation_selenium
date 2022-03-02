package com.airtel.cs.model.response.am;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {
    private String profileId;
    private String profileName;
    private String currency;
    private String minResidualBalance;
    private String maxBalance;
    private String minTxnAmount;
    private String maxTxnAmount;
    private String maxPctTransferAllowed;
    private String userType;
    private Bearer bearer;
    private Payer payer;
}
