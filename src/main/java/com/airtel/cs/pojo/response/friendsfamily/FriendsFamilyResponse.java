package com.airtel.cs.pojo.response.friendsfamily;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class FriendsFamilyResponse {
    private String fafNumber;
    private String owner;
    private String fafGroup;
    private Boolean fafMaxAllowedNumbersReachedFlag;
    private String startDate;
    private String endDate;
}
