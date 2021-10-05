package com.airtel.cs.model.request.issue;

import com.airtel.cs.model.response.issue.IssueResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class issueRequest {
    private String status;
    private String statusCode;
    private IssueResult result;
    private String message;
}
