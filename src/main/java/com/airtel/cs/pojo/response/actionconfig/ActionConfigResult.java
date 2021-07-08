package com.airtel.cs.pojo.response.actionconfig;

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
  private List<Reason> reasons;
  private Boolean showPopup;
  private Boolean hasPermission;
  private List<String> roles;
  private Boolean actionEnabled;
  private List<Condition> conditions;
  private Boolean raiseSRButtonEnabled;
}
