package com.airtel.cs.pojo.ticketlist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Interactions {

    private int interactionId;

    private String issue;

    private String customerId;

    private Channel channel;

    private String disposition;

    private String interactionType;

    private String comment;

    private String createdOn;

    private int issueCount;

    private String agent;

}
