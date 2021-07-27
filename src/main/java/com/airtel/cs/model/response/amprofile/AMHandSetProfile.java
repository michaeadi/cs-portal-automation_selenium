package com.airtel.cs.model.response.amprofile;

import com.airtel.cs.model.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AMHandSetProfile {
    private String message;
    private Integer statusCode;
    private String status;
    private ResultAMHandSetProfile result;
    private APIErrors apiErrors;
}
