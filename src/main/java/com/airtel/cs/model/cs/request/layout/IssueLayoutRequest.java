package com.airtel.cs.model.cs.request.layout;

import com.airtel.cs.model.cs.request.issue.IssueDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueLayoutRequest {
    private String status;
    private Integer statusCode;
    private ArrayList<IssueDetails> result;
    private String message;
}
