package com.airtel.cs.model.cs.request.am;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TcpLimitsRequest {
    private String msisdn;
    private String tcpId;
    private String userType;
    private String bearer;
}
