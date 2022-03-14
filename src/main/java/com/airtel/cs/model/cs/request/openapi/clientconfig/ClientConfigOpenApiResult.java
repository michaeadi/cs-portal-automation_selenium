package com.airtel.cs.model.cs.request.openapi.clientconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientConfigOpenApiResult {
    private Integer id;
    private Boolean searchable;
    private String fieldName;
    private Boolean isActive;
    private Boolean mandatory;
    private String iconName;
    private String label;
    private String client;
}
