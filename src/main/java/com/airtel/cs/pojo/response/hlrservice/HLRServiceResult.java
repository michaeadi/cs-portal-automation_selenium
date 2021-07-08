package com.airtel.cs.pojo.response.hlrservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HLRServiceResult {
    private String serviceName;
    private String serviceDesc;
    private ArrayList<String> hlrCodes;
    private ArrayList<String> hlrCodeDetails;
    private String serviceStatus;
    private String type;
}
