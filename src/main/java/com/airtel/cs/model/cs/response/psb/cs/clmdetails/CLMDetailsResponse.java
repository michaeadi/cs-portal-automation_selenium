package com.airtel.cs.model.cs.response.psb.cs.clmdetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CLMDetailsResponse implements Serializable{
    private String message;
    private Integer statusCode;
    private AMUserProfileApiResponse result;
    private String status;
}
