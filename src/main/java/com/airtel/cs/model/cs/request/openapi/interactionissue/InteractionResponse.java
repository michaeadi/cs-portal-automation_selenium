package com.airtel.cs.model.cs.request.openapi.interactionissue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionResponse implements Serializable {

  private static final long serialVersionUID = -2755342631850755320L;

  private String interactionId;

  private List<Issue> issues;

  private String customerId;

  private InteractionChannel channel;

  private Long duration;

  private boolean isFinalSubmit = false;

  private boolean existingCustomer;

  private String createdBy;

  private Timestamp createdOn;

  private String updatedBy;

  private Timestamp updatedOn;

  private Map<String, String> clientInfo;

  private String sourceApp;

}
