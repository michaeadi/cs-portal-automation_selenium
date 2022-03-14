package com.airtel.cs.model.cs.response.plans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlansResult {
    private LastRecharge lastRecharge;
    private MainAccountBalance mainAccountBalance;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    private Voice voice;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Data data;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private Sms sms;
    private String dataManager;
}


