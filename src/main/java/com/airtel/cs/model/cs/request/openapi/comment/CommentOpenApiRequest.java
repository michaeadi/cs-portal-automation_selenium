package com.airtel.cs.model.cs.request.openapi.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentOpenApiRequest {

  private Long id;
  private String ticketId;
  private Long agentId;
  private String agentName;
  private String comment;
}
