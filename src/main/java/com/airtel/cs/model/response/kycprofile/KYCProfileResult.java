package com.airtel.cs.model.response.kycprofile;

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
public class KYCProfileResult {

    private String activationDate;
    private String sim;
    private String simType;
    private String status;
    private String lineType;
    private String serviceCategory;
    private String segment;
    private String subSegment;
    private String serviceClass;
    private String customerType;
    private Boolean vip;
    private Boolean gsmAdditionalInfo;
    private String modifiedBy;
    private String modifiedDate;
    private String reason;
    private ArrayList<PUK> puk;
    private String pin1;
    private String pin2;
}
