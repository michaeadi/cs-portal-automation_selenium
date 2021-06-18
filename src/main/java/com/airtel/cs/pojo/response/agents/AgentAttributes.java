package com.airtel.cs.pojo.response.agents;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentAttributes {
    private AdditionalDetails additionalDetails;
    private UserDetails userDetails;
}