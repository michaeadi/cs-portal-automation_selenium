package com.airtel.cs.model.response.filedmasking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldMaskConfigs {

  private String id;
  private String key;
  private String attribute;
  private String thresholdValue;
  private String operator;
  private String thresholdType;
  private Set<String> roles;
  private Integer digitsVisible;
  private Date createdOn;
  private String createdBy;
  private Date updatedOn;
  private String updatedBy;
}
