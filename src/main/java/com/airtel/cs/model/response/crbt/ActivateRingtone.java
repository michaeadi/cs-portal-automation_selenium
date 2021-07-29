package com.airtel.cs.model.response.crbt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivateRingtone {
    private String message;
    private String status;
    private String statusCode;
    private Map<String,String> apiErrors;
    private Integer totalCount;
    private RingtoneResult result;
}
