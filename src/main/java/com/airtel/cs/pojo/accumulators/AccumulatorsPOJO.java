package com.airtel.cs.pojo.accumulators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class AccumulatorsPOJO {
    private String message;
    private String status;
    private Integer statusCode;
    private com.airtel.cs.pojo.apiErrors apiErrors;
    private int totalCount;
    private ArrayList<AccumulatorResult> result;
}
