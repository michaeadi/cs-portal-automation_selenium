package com.airtel.cs.pojo.response.parentcategory;

import com.airtel.cs.pojo.response.filedmasking.FieldMaskConfigs;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentCategoryResponse {

  private Integer statusCode;
  private String status;
  private Map<String, List<Category>> result;
}
