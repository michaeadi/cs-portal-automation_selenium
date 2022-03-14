package com.airtel.cs.model.cs.response.customeprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDetailsResponse {

    private String status;
    private Integer statusCode;
    private CustomerDetails result;
    private Map<String, String> apiErrors;
}
