package com.airtel.cs.model.cs.response.interactionissue;

import com.airtel.cs.model.cs.request.issue.IssueDetail;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionIssueResult {
    private Integer interactionId;
    private IssueDetail issues;
    private InteractionTicketDetail ticket;
}
