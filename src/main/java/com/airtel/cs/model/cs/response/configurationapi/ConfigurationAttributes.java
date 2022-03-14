package com.airtel.cs.model.cs.response.configurationapi;

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
public class ConfigurationAttributes {
    private Integer id;
    private Boolean enabled;
    private String created;
    private String updated;
    private String opco;
    private String  key;
    private String value;
}
