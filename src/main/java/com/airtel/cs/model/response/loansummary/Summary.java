package com.airtel.cs.model.response.loansummary;

import com.airtel.cs.model.response.vendors.ApiErrors;
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
public class Summary {
    private String status;
    private String statusCode;
    private Details result;
    private ApiErrors apiErrors;
    private String message;
}
