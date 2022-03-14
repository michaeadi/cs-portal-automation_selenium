package com.airtel.cs.model.cs.response.actionconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActionConfigResult {
  private String id;
  private String actionKey;
  private String categoryId;
  private List<Reason> reasons;
  private List<MetaInfo> metaInfo;
  private Boolean showPopup;
  private Boolean hasPermission;
  private List<String> roles;
  private Boolean actionEnabled;
  private List<Condition> conditions;
  private Boolean raiseSRButtonEnabled;
}
