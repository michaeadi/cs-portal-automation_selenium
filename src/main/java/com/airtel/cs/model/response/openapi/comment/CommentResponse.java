package com.airtel.cs.model.response.openapi.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {

  private Long id;
  private String comment;
  private String commentType;
  private Long agentId;
  private String agentName;
  private Long createdOn;
  private Long updatedOn;
}
