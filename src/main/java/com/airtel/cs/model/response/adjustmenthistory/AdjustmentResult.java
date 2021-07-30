package com.airtel.cs.model.response.adjustmenthistory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdjustmentResult {
    private String date;
    private String adjustmentType;
    private String reason;
    private String accountType;
    private String amount;
    private String agentId;
    private String agentName;
    private String comment;
}
