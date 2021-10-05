package com.airtel.cs.model.request.ticketdetail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExternalStates {
    private InternalState internalState;

    private String isReopenedState;

    private String active;

    private String id;

    private String externalStateName;
}
