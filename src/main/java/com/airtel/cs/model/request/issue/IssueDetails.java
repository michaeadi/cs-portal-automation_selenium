package com.airtel.cs.model.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueDetails {
    private String label;
    private String pattern;
    private String fieldName;
    private String fieldType;
    private Boolean mandatory;
    private String placeHolder;
    private String fieldValue;
    private List<String> fieldOptions;
}
