package com.airtel.cs.model.response.accountinfo;
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
public class AccountDetails {
    private String status;
    private Integer statusCode;
    private Integer pageSize;
    private Integer pageNumber;
    private String startDate;
    private String endDate;
    private Integer totalCount;
    private List<AccountDetailResponse> result;
}
