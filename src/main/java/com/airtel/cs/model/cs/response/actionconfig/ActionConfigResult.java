package com.airtel.cs.model.cs.response.actionconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
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
  private ArrayList<Reason> reasons;
  private ArrayList<MetaInfo> metaInfo;
  private boolean showPopup;
  private boolean hasPermission;
  private ArrayList<String> roles;
  private Boolean actionEnabled;
  private ArrayList<Condition> conditions;
  private boolean raiseSRButtonEnabled;
  public String defaultReason;
  public String defaultComment;
  public ArrayList<String> permission;
}
