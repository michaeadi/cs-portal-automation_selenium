package com.airtel.cs.model.cs.request.ticketdetail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QueueStates {

    private InternalState internalState;

    private boolean isReopenedState;

    private boolean active;

    private int id;

    private String externalStateName;
}
