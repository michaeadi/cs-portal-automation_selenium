package com.airtel.cs.model.cs.request.openapi.interactionissue;

import com.airtel.cs.model.cs.request.openapi.ticket.TicketDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueTicketDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -4621188586726602539L;

  private Long interactionId;

  private Issue issues;

  private TicketDTO ticket;

  @JsonIgnore
  private boolean ticketCreated;

}
