package com.airtel.cs.model.cs.request.openapi.ticket;

import com.airtel.cs.model.cs.request.openapi.category.CategoryHierarchyOpenApi;
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
public class TicketHistoryLogOpenRequest {
    private String status;
    private Integer statusCode;
    private String message;
    private Map<String, ArrayList<CategoryHierarchyOpenApi>> result;
}
