package com.airtel.cs.model.request.tickethistory;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketHistoryResult {
    private String ticketId;
    private String sourceApp;
    private int issueId;
    private List<CategoryLevel> categoryLevel;
    private TicketPool ticketPool;
    private boolean customerSlaBreached;
    private boolean workgroupSlaBreached;
    private Long agentId;
    private String assigneeName;

}
