package com.airtel.cs.model.response.vendors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorNames {
    private String status;
    private String statusCode;
    private Result result;
    private ApiErrors apiErrors;
    private Integer totalCount;
}
