package com.airtel.cs.model.cs.request.categoryhierarchy;

import com.airtel.cs.model.cs.request.issue.CategoryHierarchy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryHierarchyRequest {
    private String status;
    private Integer statusCode;
    private Map<String, ArrayList<CategoryHierarchy>> result;
    private String message;
}
