package com.airtel.cs.model.response.configurationapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ConfigurationResult {
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private List<ConfigurationAttributes> result;
}
