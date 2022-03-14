package com.airtel.cs.model.cs.request.openapi.ticket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchTicketOpenRequest {
    private String status;
    private Integer statusCode;
    private List<SearchTicketOpenApiResult> result;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private String message;
}
