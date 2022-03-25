package com.airtel.cs.model.cs.response.psb.downstream;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileDetails {

    private String msisdn;
    private List<Subscriptions> subscriptions;
    private Boolean isUser;
    private Boolean walletsBarred;
    private String userType;
    private BarDetail walletsBarredDetails;
    private String walletUserId;
}