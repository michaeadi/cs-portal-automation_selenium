package com.airtel.cs.pojo.response.hlrservice;

import com.airtel.cs.pojo.response.APIErrors;
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
public class HLRServicePOJO {
    private String message;
    private String status;
    private int statusCode;
    private APIErrors apiErrors;
    private int totalCount;
    private List<HLRServiceResult> result;
}
