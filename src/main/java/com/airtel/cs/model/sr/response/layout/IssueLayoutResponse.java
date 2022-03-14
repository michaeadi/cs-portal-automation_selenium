package com.airtel.cs.model.sr.response.layout;

import com.airtel.cs.model.sr.response.issue.IssueDetailsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueLayoutResponse {
    private String status;
    private Integer statusCode;
    private ArrayList<IssueDetailsResponse> result;
    private String message;
}
