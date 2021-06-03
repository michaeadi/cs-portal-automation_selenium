package com.airtel.cs.pojo.response.accumulators;

import com.airtel.cs.pojo.response.APIErrors;
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
    private APIErrors apiErrors;
    private int totalCount;
    private ArrayList<AccumulatorResult> result;
}
