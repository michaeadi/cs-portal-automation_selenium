package com.airtel.cs.pojo.response.actiontrail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionTrailPOJO {
    private String status;
    private Integer statusCode;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private ArrayList<EventResult> result;
}
