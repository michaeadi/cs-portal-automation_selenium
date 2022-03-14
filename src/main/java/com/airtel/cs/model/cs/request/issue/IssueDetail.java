package com.airtel.cs.model.cs.request.issue;

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
public class IssueDetail {
    private String issueId;
    private ArrayList<CategoryHierarchy> categoryHierarchy;
    private ArrayList<IssueDetailsResponse> issueDetailResponses;
    private String comment;
    private Boolean actionPerformed;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private TicketDetail ticket;
}
