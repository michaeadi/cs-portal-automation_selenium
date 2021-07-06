package com.airtel.cs.pojo.response.actionconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Reason {
  private String key;
  private String fieldType;
  private String mandatory;
  private String placeHolder;
  private List<String> fieldOptions;
  private Boolean isActionType;
}
