package com.airtel.cs.model.request.issue;

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
    private ArrayList<IssueDetails> issueDetails;
    private String comment;
    private Boolean actionPerformed;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private TicketDetail ticket;
}
