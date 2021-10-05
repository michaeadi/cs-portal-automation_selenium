package com.airtel.cs.model.request.openapi.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class ExternalStateDTO {
    private Long id;

    private String externalStateName;

    private Boolean isReopenedState;

    private InternalStateDTO internalState;

    private boolean isActive;

    private List<TicketPoolRequest> ticketPool;

}

