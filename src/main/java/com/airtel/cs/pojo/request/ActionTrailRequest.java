package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActionTrailRequest {
    String msisdn;
    String eventType;
    int pageSize;
    int pageNumber;
}
