package com.airtel.cs.pojo.response.crbt;

import com.airtel.cs.pojo.response.APIErrors;
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
public class Top20Ringtone {

    private String message;
    private String status;
    private String statusCode;
    private APIErrors apiErrors;
    private int totalCount;
    private ArrayList<RingtoneResult> result;
}
