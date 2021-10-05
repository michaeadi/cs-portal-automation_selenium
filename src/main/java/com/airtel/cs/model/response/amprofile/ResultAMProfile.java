package com.airtel.cs.model.response.amprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultAMProfile {

    private String accountStatus;
    private String regStatus;
    private String walletType;
    private String grade;
    private String firstName;
    private String lastName;
    private String dob;
    private BarDetails barDetails;
    private ArrayList<Wallet> wallets;
    private String isPinReset;
    private boolean pinSet;
    private boolean userBarred;
    private String serviceStatus;

}