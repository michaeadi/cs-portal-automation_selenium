package com.airtel.cs.pojo.response.filedmasking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldMaskConfigReponse {

  private String message;
  private String status;
  private String statusCode;
  private FieldMaskConfigs result;
}
