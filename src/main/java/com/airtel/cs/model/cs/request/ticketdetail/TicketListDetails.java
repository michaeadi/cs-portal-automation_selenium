package com.airtel.cs.model.cs.request.ticketdetail;

import com.airtel.cs.model.cs.request.issue.IssueDetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketListDetails {
    private String ticketId;
    private String customerSla;
    private PriorityRequest priority;
    private StateRequest state;
    private String createdOn;
    private String workgroupSla;
    private TicketPool queue;
    private String workGroup;
    private ArrayList<CategoryLevel> categoryLevel;
    private String agentName;
    private String agentAuuid;
    private CreatedBy createdBy;
    private String closedDate;
    private String issueId;
    private String agentId;
    private ArrayList<Comments> comments;
    private ArrayList<QueueStates> queueStates;
    private ArrayList<IssueDetails> issueDetails;
    private ArrayList<Interactions> interactions;
    private ArrayList<IssueDetails> ticketDetails;
    private String committedSla;
    private String actionPerformed;
    private String expectedClosureDate;
    private String comment;
    private String assignee;
    private String msisdn;
    private Map<String, Long> sla;
    private String sourceApp;
}
