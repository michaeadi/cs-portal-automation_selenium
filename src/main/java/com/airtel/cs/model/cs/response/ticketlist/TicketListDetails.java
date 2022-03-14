package com.airtel.cs.model.cs.response.ticketlist;

import com.airtel.cs.model.sr.response.issue.IssueDetailsResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketListDetails {
    private String ticketId;
    private String customerSla;
    private Priority priority;
    private State state;
    private String createdOn;
    private String workgroupSla;
    private TicketPool ticketPool;
    private String workGroup;
    private ArrayList<CategoryLevel> categoryLevel;
    private String agentName;
    private String agentAuuid;
    private CreatedBy createdBy;
    private String closedDate;
    private String issueId;
    private String agentId;
    private List<Comments> comments;
    private List<QueueStates> queueStates;
    private List<IssueDetailsResponse> issueDetailResponses;
    private List<Interactions> interactions;
    private List<IssueDetailsResponse> ticketDetails;
    private String committedSla;
    private String actionPerformed;
    private String expectedClosureDate;
    private String comment;
    private String assignee;
    private String msisdn;
    private Map<String, Long> sla;
}
