package com.airtel.cs.model.response.configurationapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ConfigurationList {
    private String status;
    private Integer statusCode;
    private String message;
    private ConfigurationResult result;
}
