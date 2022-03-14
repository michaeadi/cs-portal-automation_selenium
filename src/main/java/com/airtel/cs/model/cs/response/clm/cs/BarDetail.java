package com.airtel.cs.model.cs.response.clm.cs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BarDetail implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6544872958071814471L;

    private String barReason;
    private String remarks;
    private String barredBy;
    private String barredOn;
    private String barType;
}