package com.airtel.cs.pojo.response.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LockedSection {
    private String key;
    private Integer policyId;
    private Boolean isAuthenticated;
}
