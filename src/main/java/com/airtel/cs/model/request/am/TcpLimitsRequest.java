package com.airtel.cs.model.request.am;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TcpLimitsRequest {
    private String msisdn;
    private String tcpId;
}
