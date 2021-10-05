package com.airtel.cs.model.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketPoolDetail {
    private Integer id;
    private String ticketPoolName;
    private ArrayList<ExternalStates> externalStates;
    private Boolean active;
}
