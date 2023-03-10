package com.airtel.cs.model.cs.response.clientconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllConfiguredClientRespose {
    private String client;
    private String fieldName;
    private String iconName;
    private String label;
}
