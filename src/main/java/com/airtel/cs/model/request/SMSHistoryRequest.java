package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SMSHistoryRequest {
    private String receiverId;
    private Integer pageSize;
    private Integer pageNumber;
}
