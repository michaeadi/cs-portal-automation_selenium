package com.airtel.cs.model.cs.response.amprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Wallet {
    private String balance;
    private String currency;
    private String tcpId;
    private String walletType;
    private String fundsInClearance;
    private String frozen;
    private String primary;
    private String totalDebit;
    private String totalCredit;
    private String status;
    private String nubanId;
}