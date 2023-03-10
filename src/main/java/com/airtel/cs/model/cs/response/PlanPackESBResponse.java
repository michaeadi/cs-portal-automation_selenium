package com.airtel.cs.model.cs.response;

import com.airtel.cs.model.cs.response.planpack.AddonUsage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlanPackESBResponse {
    public String status;
    public String message;
    public List<Object> planUsage = null;
    public AddonUsage addonUsage;
}
