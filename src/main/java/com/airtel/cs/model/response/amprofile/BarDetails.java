package com.airtel.cs.model.response.amprofile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarDetails {

    @JsonProperty("bar_reason")
    private String barReason;

    private String remarks;

    @JsonProperty("barred_by")
    private String barredBy;

    @JsonProperty("barred_on")
    private String barredOn;

    @JsonProperty("bar_type")
    private String barType;
}
