package com.airtel.cs.model.response.plans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Voice {
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private String balance;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private long expireTime;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private ArrayList<Accounts> accounts;
}
