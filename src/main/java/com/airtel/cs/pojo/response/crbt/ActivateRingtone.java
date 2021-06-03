package com.airtel.cs.pojo.response.crbt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivateRingtone {
    private String message;
    private String status;
    private String statusCode;
    private Map<String,String> apiErrors;
    private int totalCount;
    private RingtoneResult result;
}
