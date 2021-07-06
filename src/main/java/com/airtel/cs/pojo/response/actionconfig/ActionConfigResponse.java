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
public class ActionConfigResponse {

  private String status;
  private Integer statusCode;
  private List<ActionConfigResult> result;
}
