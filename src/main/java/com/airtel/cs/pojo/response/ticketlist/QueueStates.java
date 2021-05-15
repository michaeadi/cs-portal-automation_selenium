package com.airtel.cs.pojo.response.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueStates {

    private InternalState internalState;

    private boolean isReopenedState;

    private boolean active;

    private int id;

    private String externalStateName;
}
