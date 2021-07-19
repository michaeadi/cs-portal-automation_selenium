package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionTrailRequest {
    private String msisdn;
    private String eventType;
    private int pageSize;
    private int pageNumber;
}
