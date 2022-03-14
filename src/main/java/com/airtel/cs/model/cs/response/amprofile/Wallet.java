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

    @JsonProperty("wallet_type")
    private String walletType;

    @JsonProperty("funds_in_clearance")
    private String fundsInClearance;

    private String frozen;

    @JsonProperty("is_primary")
    private String primary;

    @JsonProperty("total_debit")
    private String totalDebit;

    @JsonProperty("total_credit")
    private String totalCredit;

}