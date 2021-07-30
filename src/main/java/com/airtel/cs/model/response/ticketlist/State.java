package com.airtel.cs.model.response.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class State {

    private Integer id;
    private String externalStateName;
    private Boolean isReopenedState;
    private InternalState internalState;
    private Boolean active;
}

