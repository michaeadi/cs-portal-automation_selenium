package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionHistoryPOJO {

    String status;
    int statusCode;
    int pageSize;
    int pageNumber;
    int totalCount;
    ArrayList<ResultRechargeHistoryPOJO> result;
    APIErrors apiErrors;
}
