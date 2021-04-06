package com.airtel.cs.pojo.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EscalationLevelDetails {
    private String level;

    private String name;

    private String email;
}
