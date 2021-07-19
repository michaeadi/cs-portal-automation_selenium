package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SaveAgentLimitRequest {
    private String roleId;
    private List<LimitConfigRequest> limitConfig;
}
