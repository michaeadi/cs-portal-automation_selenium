package com.airtel.cs.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PackUsageDTO {
    public String bundleName;
    public String category;
    public String benefit;
    public String used;
    public String unit;
    public String available;
}
