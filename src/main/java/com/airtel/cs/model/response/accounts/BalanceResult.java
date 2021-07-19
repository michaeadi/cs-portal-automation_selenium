package com.airtel.cs.model.response.accounts;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BalanceResult {
    private String daId;
    private String daDesc;
    private String currentDaBalance;
    @JsonProperty("bundleType")
    private String bundleType;
    private Long expiryDate;
}
