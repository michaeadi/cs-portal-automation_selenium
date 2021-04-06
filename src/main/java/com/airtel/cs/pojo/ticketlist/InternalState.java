package com.airtel.cs.pojo.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalState {

    private int id;
    private String internalStateName;

}
