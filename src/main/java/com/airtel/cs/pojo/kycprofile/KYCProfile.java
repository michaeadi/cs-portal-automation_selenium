package com.airtel.cs.pojo.kycprofile;

import com.airtel.cs.pojo.apiErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KYCProfile {
    private String status;
    private String statusCode;
    private KYCProfileResult result;
    private apiErrors apiErrors;
}
