package com.airtel.cs.model.cs.response.postpaid.enterprise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatementCSResponse {
    public String status;
    public Integer statusCode;
    public Integer pageSize;
    public Integer pageNumber;
    public Integer totalCount;
    public Result result;
}
