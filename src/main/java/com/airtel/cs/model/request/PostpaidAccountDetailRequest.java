package com.airtel.cs.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostpaidAccountDetailRequest {
    public String accountNo;
    public String fromDate;
    public String endDate;
    public String pageNumber;
    public String pageSize;
}
