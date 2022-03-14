package com.airtel.cs.model.cs.request.openapi.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TicketDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5312744523927327088L;

    private String ticketId;

    private String sourceApp;

    private Long issueId;

    private Priority priority;

    private TicketPoolDTO ticketPool;

    private ExternalStateDTO state;

    private Long createdOn;

    private BaseEntity createdBy;

    private Long sla;

    private Long currentSla;

    private Long committedSla;

    private List<Object> ticketDetails;

    private Long expectedClosureDate;

    private String id;

    private Long agentId;

    private boolean assigned;

    private boolean isWorkgroupSlaBreached;

    private boolean isCustomerSlaBreached;

    private boolean reopen;

    private Timestamp slaBreachedOn;

    private Timestamp reopenedOn;

    private Timestamp closedOn;

    private Long customerClosedSla;

    private List<CategoryDTO> categoryLevel;
}