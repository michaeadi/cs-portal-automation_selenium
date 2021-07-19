package com.airtel.cs.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SMSHistoryRequest {
    private String receiverId;
    private int pageSize;
    private int pageNumber;
}
