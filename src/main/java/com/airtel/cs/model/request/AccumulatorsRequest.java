package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccumulatorsRequest {
    private String msisdn;
    private Integer pageSize;
    private Integer pageNumber;
}
