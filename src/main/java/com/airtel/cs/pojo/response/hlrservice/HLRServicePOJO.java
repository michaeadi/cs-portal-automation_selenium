package com.airtel.cs.pojo.response.hlrservice;

import com.airtel.cs.pojo.response.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HLRServicePOJO {
    private String message;
    private String status;
    private String statusCode;
    private APIErrors apiErrors;
    private int totalCount;
    private List<HLRServiceResult> result;
}
