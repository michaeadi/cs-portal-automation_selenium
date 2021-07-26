package com.airtel.cs.model.response.rechargehistory;

import com.airtel.cs.model.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionHistoryPOJO {

    private String status;
    private Integer statusCode;
    private Integer pageSize;
    private Integer pageNumber;
    private Integer totalCount;
    private List<ResultRechargeHistory> result;
    private APIErrors apiErrors;
}
