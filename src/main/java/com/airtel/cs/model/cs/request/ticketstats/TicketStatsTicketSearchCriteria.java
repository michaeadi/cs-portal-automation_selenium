package com.airtel.cs.model.cs.request.ticketstats;

import com.airtel.cs.commonutils.applicationutils.enums.TicketSortType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@AllArgsConstructor
public class TicketStatsTicketSearchCriteria {
    /*private String clientInfo;
    @Nullable
    private String fromDate;
    @Nullable
    private String toDate;
    @Nullable
    private String ticketId;
    @Nullable
    private String days;
    @Nullable
    private String stateIds;*/


    private TicketSortType sortType;
    private String fromDate;
    private String toDate;
    private List<Long> categoryIds;
    private Set<Long> stateIds;
    private int days;
    public String ticketId;

    private Integer categoryLevel;

    private List<String> categoryLevelValues;

    private List<String> assignedQueues;
    private Boolean customerSlaBreached;
    private Boolean workgroupSlaBreached;

    private List<String> workgroups;

    private List<String> assigneeIds;

    private List<String> assigneeNames;

    private Map<String, String> clientInfo;

    public TicketStatsTicketSearchCriteria(String clientConfig, Object o, Object o1, Object o2, Object o3, String externalStateIds) {

    }

    public TicketStatsTicketSearchCriteria() {

    }
}