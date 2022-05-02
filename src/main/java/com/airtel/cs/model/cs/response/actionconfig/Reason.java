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
public class Reason {
  private String key;
  private String fieldType;
  private String placeHolder;
  private ArrayList<Object> fieldOptions;
  private Object isActionType;
}
