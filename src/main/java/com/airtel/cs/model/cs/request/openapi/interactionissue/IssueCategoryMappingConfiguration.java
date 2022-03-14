package com.airtel.cs.model.cs.request.openapi.interactionissue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssueCategoryMappingConfiguration implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5465240070107918507L;


  private Long id;

  public String category;

  private int level;

  private IssueCategoryMappingConfiguration parentCategory;

  private List<IssueCategoryMappingConfiguration> childCategory;

  private boolean isActive;

  public String categoryLabelName;

  public String categoryType;

  private boolean isCommentable;



}
