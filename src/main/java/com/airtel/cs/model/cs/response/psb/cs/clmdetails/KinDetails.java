package com.airtel.cs.model.cs.response.psb.cs.clmdetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KinDetails {
    public String firstName;
    public String lastName;
    public String msisdn;
}