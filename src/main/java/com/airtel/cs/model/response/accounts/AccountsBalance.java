package com.airtel.cs.model.response.accounts;

import com.airtel.cs.model.response.apierror.APIErrors;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Getter
@ToString
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountsBalance {
    private String message;
    private String status;
    private Integer statusCode;
    private Integer totalCount;
    private List<BalanceResult> result;
    private APIErrors apiErrors;
}
