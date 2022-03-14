package com.airtel.cs.model.cs.response.agentlimit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LimitConfig {
    private String featureKey;
    private String displayName;
    private String unit;
    private Integer dailyLimit;
    private Integer monthlyLimit;
    private Integer transactionLimit;
}
