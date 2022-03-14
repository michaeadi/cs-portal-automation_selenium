package com.airtel.cs.model.cs.response.clm.downstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSearchReponse implements Serializable {
    private String firstName;
    private String lastName;
    private String gender;
    private String msisdn;
    private String emailId;
    private String dob;
    private String primaryIdType;
    private String primaryIdNumber;
    private String placeOfBirth;
    private String middleName;
    private String nationality;
    private String customerType;
    private String secondaryIdType;
    private String secondaryIdNumber;
    private String customerId;
    private String customerStatus;
    private String customerCategory;
    private String customerSubCategory;
    private String correlationId;
    private String residentType;
    private String alternatePhoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String district;
    private String zone;
    private String village;
    private String motherMaidenName;
    private String customerSignature;
    private String primaryIdTypeResourceFront;
    private String primaryIdTypeResourceBack;
    private List<UserProfileDetails> details;
}