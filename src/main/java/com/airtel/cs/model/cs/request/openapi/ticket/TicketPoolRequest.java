package com.airtel.cs.model.cs.request.openapi.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketPoolRequest implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 749477370237054610L;
  private List<String> ticketId;

}
