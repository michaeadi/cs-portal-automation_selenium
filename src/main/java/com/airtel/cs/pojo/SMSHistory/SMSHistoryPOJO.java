package com.airtel.cs.pojo.SMSHistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSHistoryPOJO {
    private String status;
    private int statusCode;
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    private List<SMSHistoryList> result;
    private String message;
}
