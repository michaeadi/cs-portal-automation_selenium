package com.airtel.cs.pojo.response.filedmasking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
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
