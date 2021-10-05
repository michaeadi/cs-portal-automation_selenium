package com.airtel.cs.commonutils.dataproviders.databeans;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientConfigDataBean {
    private String firstCategoryLevel;
    private String LastCategoryLevel;
    private String fieldName;
    private String isSearchAble;
    private String isActive;
    private String isMandatory;
    private String value;
    private String isDeActivate;
}
