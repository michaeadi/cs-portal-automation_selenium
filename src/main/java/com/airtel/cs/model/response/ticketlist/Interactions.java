package com.airtel.cs.model.response.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Interactions {

    private Integer interactionId;

    private String issue;

    private String customerId;

    private Channel channel;

    private String disposition;

    private String interactionType;

    private String comment;

    private String createdOn;

    private Integer issueCount;

    private String agent;

}
