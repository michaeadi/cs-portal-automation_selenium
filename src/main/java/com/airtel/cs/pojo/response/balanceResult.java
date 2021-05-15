package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class balanceResult {
    String daId;
    String daDesc;
    String currentDaBalance;
    @JsonProperty("bundleType")
    String bundleType;
    long expiryDate;
}
