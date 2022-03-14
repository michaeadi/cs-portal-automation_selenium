package com.airtel.cs.model.cs.request.issue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryHierarchy {
    private Integer id;
    private String category;
    private Integer level;
    private String categoryLabelName;
    private String categoryType;
    private Boolean commentable;
    private Boolean active;
    private String categoryName; //Used for Issue History
    private Boolean isLastLevel;
}
