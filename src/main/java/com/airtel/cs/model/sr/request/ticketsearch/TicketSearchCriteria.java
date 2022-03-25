package com.airtel.cs.model.sr.request.ticketsearch;
import java.util.List;
import java.util.Map;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketSearchCriteria {

    public TicketSearchCriteria(Map<String, String> clientInfo) {
        this.clientInfo=clientInfo;
    }

    private Map<String, String> clientInfo;

    private String fromDate;

    private String toDate;

    private List<Long> categoryIds;

    private Set<Long> stateIds;

    private int days;

    public  String ticketId;

    private Integer categoryLevel;

    private List<String> categoryLevelValues;

    private  List<String> assignedQueues;

    private Boolean customerSlaBreached;

    private Boolean workgroupSlaBreached;

    private  List<String> workgroups;

    private  List<String> assigneeIds;

    private  List<String> assigneeNames;

    private List<IssueFields> issueFields;
    private  String searchType;
}
