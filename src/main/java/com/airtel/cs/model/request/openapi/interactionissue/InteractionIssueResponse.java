package com.airtel.cs.model.request.openapi.interactionissue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionIssueResponse {
    private InteractionResponse interaction;
    private List<IssueTicketDTO> issues;
}
