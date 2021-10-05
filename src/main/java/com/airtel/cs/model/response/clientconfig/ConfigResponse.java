package com.airtel.cs.model.response.clientconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConfigResponse {
    private Integer id;
    private String client;
    private Boolean searchable;
    private String fieldName;
    private Boolean isActive;
    private Boolean mandatory;
}
