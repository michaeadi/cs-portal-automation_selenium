package com.airtel.cs.model.cs.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssueRequest {
    private String interactionId;
    private String comment;
    private String createdBy;
    private IssueDetails result;
    private CategoryHierarchy category;
    private MetaInfo metainfo;
}
