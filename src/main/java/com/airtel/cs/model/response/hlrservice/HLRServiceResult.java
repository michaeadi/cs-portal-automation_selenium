package com.airtel.cs.model.response.hlrservice;

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
public class HLRServiceResult {
    private String serviceName;
    private String serviceDesc;
    private List<String> hlrCodes;
    private List<String> hlrCodeDetails;
    private String serviceStatus;
    private String type;
}
