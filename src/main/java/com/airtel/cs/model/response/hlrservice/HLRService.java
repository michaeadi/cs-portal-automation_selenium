package com.airtel.cs.model.response.hlrservice;

import com.airtel.cs.model.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLRService {
    private String message;
    private String status;
    private Integer statusCode;
    private APIErrors apiErrors;
    private Integer totalCount;
    private List<HLRServiceResult> result;
}
