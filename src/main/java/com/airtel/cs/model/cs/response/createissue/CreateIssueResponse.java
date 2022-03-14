package com.airtel.cs.model.cs.response.createissue;

import com.airtel.cs.model.cs.response.interactionissue.InteractionIssueResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssueResponse {
    private String message;
    private String status;
    private Integer statusCode;
    private InteractionIssueResult result;
}
