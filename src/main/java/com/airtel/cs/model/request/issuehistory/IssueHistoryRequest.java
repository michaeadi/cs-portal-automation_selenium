package com.airtel.cs.model.request.issuehistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueHistoryRequest {
    private String status;
    private Integer statusCode;
    private ArrayList<IssueList> result;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private String message;
}
