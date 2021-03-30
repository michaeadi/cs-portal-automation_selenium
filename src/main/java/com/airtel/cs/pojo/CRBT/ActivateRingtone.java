package com.airtel.cs.pojo.CRBT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActivateRingtone {
    private String message;
    private String status;
    private String statusCode;
    private com.airtel.cs.pojo.apiErrors apiErrors;
    private int totalCount;
    private RingtoneResult result;
}
