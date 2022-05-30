package com.airtel.cs.model.cs.response.amprofile;

import com.airtel.cs.model.cs.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AMProfile {

    private String message;
    private int statusCode;
    private ResultAMProfile result;
    private APIErrors apiErrors;
    private String status;
}
