package com.airtel.cs.model.request.openapi.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TicketPoolDTO {
    private Long id;
    private String ticketPoolName;
    private boolean isActive;
    private List<ExternalStateDTO> externalStates;
}
