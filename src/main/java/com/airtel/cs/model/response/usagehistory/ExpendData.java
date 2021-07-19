package com.airtel.cs.model.response.usagehistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpendData {
    private String type;
    private String dateTime;
    private String startBalance;
    private String charges;
    private String endBalance;
    private String bundleName;
    private String time;
}