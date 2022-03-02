package com.airtel.cs.model.response.amprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultAMProfile {

    private String accountStatus;
    private String walletType;
    private String grade;
    private BarDetails barDetails;
    private String serviceStatus;
    private String message;
    private String firstName;
    private String lastName;
    private String regStatus;
    private String msisdn;
    private String isPinReset;
    private boolean userBarred;
    private boolean pinSet;
    private String dob;
    private List<Wallet> wallets;

}