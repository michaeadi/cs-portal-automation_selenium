package com.airtel.cs.model.cs.response.psb.downstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CLMCustomerSearchAPIResponse {
    private Boolean st;
    private String msgid;
    private String msg;
    private String timestamp;
    private String message;
    private int statusCode;
    private CustomerSearchReponse data;
}