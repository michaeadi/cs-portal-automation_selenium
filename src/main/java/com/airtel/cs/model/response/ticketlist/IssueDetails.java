package com.airtel.cs.model.response.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDetails {
    private String fieldName;

    private String fieldType;

    private String mandatory;

    private String fieldValue;

    private String placeHolder;
}
