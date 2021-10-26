package com.airtel.cs.model.response.customeprofile;
import com.airtel.cs.model.response.hbb.HbbUserDetailsResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProfileResponse {

    public String msisdn;
    public String customerName;
    public String firstName;
    public String lastName;
    public String customerType;
    public String tariffProfile;
    public String lineType;
    public String status;
    public String customerAccountNumber;
    public String billCycleName;
    public String serviceCategory;
    public Long activationDate;
    public CustomerAddress customerAddress;
    public String simSerialNumber;
    public String segment;
    public String subSegment;
    public String idType;
    public String idNumber;
    public Long dateOfBirth;
    public String nationality;
    public String modifiedBy;
    public Long modifiedDate;
    public String reason;
    public String simStatus;
    public String email;
    public String error;
    public String message;
    private Integer statusCode;

}
