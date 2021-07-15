package com.airtel.cs.pojo.response;

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
public class ResultAMProfilePOJO {
    String message;
    String firstName;
    String lastName;
    String regStatus;
    String msisdn;
    String isPinReset;
    boolean userBarred;
    boolean pinSet;
    String dob;
    ArrayList<walletPOJO> wallet;


}