package com.airtel.cs.model.response.amprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String message;
    private String firstName;
    private String lastName;
    private String regStatus;
    private String msisdn;
    private String isPinReset;
    private Boolean userBarred;
    private Boolean pinSet;
    private String dob;
    private ArrayList<Wallet> wallet;


}