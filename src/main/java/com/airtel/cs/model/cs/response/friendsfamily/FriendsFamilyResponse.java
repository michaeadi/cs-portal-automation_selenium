package com.airtel.cs.model.cs.response.friendsfamily;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendsFamilyResponse {
    private String fafNumber;
    private String owner;
    private String fafGroup;
    private Boolean fafMaxAllowedNumbersReachedFlag;
    private String startDate;
    private String endDate;
}
