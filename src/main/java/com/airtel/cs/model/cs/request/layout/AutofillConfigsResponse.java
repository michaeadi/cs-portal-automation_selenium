package com.airtel.cs.model.cs.request.layout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AutofillConfigsResponse {

  private String status;
  private Integer statusCode;
  private List<FieldsConfigDTO> result;
}
