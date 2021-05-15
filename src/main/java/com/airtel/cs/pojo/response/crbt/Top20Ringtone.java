package com.airtel.cs.pojo.response.crbt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Top20Ringtone {

    private String message;
    private String status;
    private String statusCode;
    private com.airtel.cs.pojo.response.apiErrors apiErrors;
    private int totalCount;
    private ArrayList<RingtoneResult> result;
}
