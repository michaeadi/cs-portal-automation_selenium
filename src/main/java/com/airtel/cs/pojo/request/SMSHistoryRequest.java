package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SMSHistoryRequest {
    String receiverId;
    int pageSize;
    int pageNumber;
}
