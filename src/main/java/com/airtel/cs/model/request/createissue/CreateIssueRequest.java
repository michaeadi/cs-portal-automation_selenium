package com.airtel.cs.model.request.createissue;

import com.airtel.cs.model.response.interactionissue.InteractionIssueResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateIssueRequest {
    private String message;
    private String status;
    private Integer statusCode;
    private InteractionIssueResult result;
}
