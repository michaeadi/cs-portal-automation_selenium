package com.airtel.cs.model.cs.response.rechargehistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RechargeBenefit {
    @JsonProperty("SMS")
    private String SMS;
    @JsonProperty("DATA")
    private String DATA;
    @JsonProperty("VOICE")
    private String VOICE;
}
