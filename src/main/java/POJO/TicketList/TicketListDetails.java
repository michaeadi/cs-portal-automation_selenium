package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketListDetails {
    private String ticketId;
    private String customerSla;
    private PriorityPOJO priority;
    private StatePOJO state;
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
    private ArrayList<Comments> comments;
    private ArrayList<QueueStates> queueStates;
    private ArrayList<IssueDetails> issueDetails;
    private ArrayList<Interactions> interactions;
    private String committedSla;
    private String actionPerformed;
    private String expectedClosureDate;
    private String comment;
    private String assignee;
    private Map<String ,Long> sla;
}
