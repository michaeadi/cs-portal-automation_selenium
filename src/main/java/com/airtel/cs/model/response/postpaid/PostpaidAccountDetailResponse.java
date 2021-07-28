package com.airtel.cs.model.response.postpaid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostpaidAccountDetailResponse {
    public String status;
    public Integer statusCode;
    public Integer pageSize;
    public Integer pageNumber;
    public Integer totalCount;
    public List<Result> result = null;
}
