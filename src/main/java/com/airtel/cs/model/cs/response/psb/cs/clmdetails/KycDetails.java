package com.airtel.cs.model.cs.response.psb.cs.clmdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KycDetails {

    private String msisdn;
    private ArrayList<WalletResponse> wallets;
    private ArrayList<AccountResponse> accounts;
    private String isPinSet;
    private String isPinReset;
    private Boolean isSecurityQuestionSet;
    private String isUser;
    private String userBarred;
    private String userType;
    private Boolean isMsisdnBlocked;
    private BarDetail barDetails;
    private String walletUserId;
}
