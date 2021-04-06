package com.airtel.cs.pojo.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueDetails {
    private String fieldName;

    private String fieldType;

    private String mandatory;

    private String fieldValue;

    private String placeHolder;
}
