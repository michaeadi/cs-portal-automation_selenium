package com.airtel.cs.model.cs.response.psb.cs.clmdetails;

import com.airtel.cs.model.cs.response.amprofile.BarDetails;
import com.airtel.cs.model.cs.response.amprofile.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AMUserProfileApiResponse implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2617541681656576041L;

    private String accountStatus;

    private String regStatus;

    private String walletType;

    private String grade;

    private String firstName;

    private String lastName;

    private String dob;

    private BarDetails barDetails;

    private List<Wallet> wallets;

    private String isPinReset;

    private String isPinSet;

    private String  userBarred;

    private String serviceStatus;

    private String userId;

    private String gender;

    private String msisdn;

    private String emailId;

    private String primaryIdType;

    private String primaryIdNumber;

    private String placeOfBirth;

    private String middleName;

    private String correlationId;

    private String nationality;

    private String secondaryIdType;

    private String secondaryIdNumber;

    private String customerType;

    private String customerId;

    private String customerCategory;

    private String customerSubCategory;

    private String residentType;

    private String alternatePhoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String addressLine3;

    private String district;

    private String zone;

    private String village;

    private String mothersMaidenName;

    private ArrayList<KycDetails> details;

    public KinDetails kinDetails;

    private String isUser;
}