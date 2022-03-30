package com.airtel.cs.model.cs.request.am;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsLogsRequest {
    private String msisdn;
    private String endDate;
    private String startDate;
    private Integer pageNumber;
    private Integer pageSize;
}
