package com.airtel.cs.model.request.layout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MappedFieldsConfigDTO {

  private String placeholder;

  private String responseField;

  private boolean editablePostFill;

  private boolean editableOnError;
}
