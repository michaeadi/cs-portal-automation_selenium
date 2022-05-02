package com.airtel.cs.model.cs.response.actiontrail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventLogsResponse {
    private String status;
    private Integer statusCode;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private List<EventResult> result;
}
