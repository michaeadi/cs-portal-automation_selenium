package com.airtel.cs.model.request.issuehistory;

import com.airtel.cs.model.request.issue.CategoryHierarchy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueList {
    private String createdOn;
    private String comment;
    private ArrayList<CategoryHierarchy> categoryHierarchy;
    private String ticketId;
    private CreatedBy createdBy;
    private String sourceApp;
}
