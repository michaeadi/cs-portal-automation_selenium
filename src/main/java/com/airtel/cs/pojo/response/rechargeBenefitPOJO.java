package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class rechargeBenefitPOJO {
    @JsonProperty("SMS")
    String SMS;
    @JsonProperty("DATA")
    String DATA;
    @JsonProperty("VOICE")
    String VOICE;
}
