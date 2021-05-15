package com.airtel.cs.pojo.response.configuration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigurationPOJO {
    private String message;
    private String status;
    private String statusCode;
    private ConfigResult result;
}
