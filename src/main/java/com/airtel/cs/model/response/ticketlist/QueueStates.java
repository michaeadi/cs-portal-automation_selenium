package com.airtel.cs.model.response.ticketlist;

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

    private Boolean isReopenedState;

    private Boolean active;

    private Integer id;

    private String externalStateName;
}
