package com.airtel.cs.model.cs.request.vas;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VasSubscriptionHistoryRequest {
    private String msisdn;
    private Integer pageSize;
}
