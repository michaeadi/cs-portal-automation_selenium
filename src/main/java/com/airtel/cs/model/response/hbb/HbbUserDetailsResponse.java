package com.airtel.cs.model.response.hbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HbbUserDetailsResponse {
    private String status;
    private Integer statusCode;
    private HbbUserDetailsResult result;



}
