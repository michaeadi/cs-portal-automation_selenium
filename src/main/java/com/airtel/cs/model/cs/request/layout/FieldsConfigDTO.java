package com.airtel.cs.model.cs.request.layout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FieldsConfigDTO {

  private String webhookUrl;

  private String methodType;

  private Map<String, Object> requestParam;

  private Map<String, Object> requestBody;

  private String header;

  private List<MappedFieldsConfigDTO> mappedFields;
}

