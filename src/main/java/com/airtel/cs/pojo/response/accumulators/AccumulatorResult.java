package com.airtel.cs.pojo.response.accumulators;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccumulatorResult {
    private String id;
    private String nextResetDate;
    private String startDate;
    private Integer value;
}