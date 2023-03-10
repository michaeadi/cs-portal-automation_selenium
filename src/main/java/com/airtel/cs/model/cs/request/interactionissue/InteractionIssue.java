package com.airtel.cs.model.cs.request.interactionissue;

import com.airtel.cs.model.cs.response.interactionissue.InteractionIssueResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionIssue {
    public List<InteractionIssueResult> issues;
}