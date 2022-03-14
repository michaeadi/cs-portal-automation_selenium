package com.airtel.cs.model.cs.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanUsageDTO {
    public String productName;
    public String category;
    public String benefit;
    public String unit;
    public String used;
    public String available;
}
