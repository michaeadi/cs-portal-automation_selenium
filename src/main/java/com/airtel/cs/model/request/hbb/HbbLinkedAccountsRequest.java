package com.airtel.cs.model.request.hbb;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HbbLinkedAccountsRequest {
    private String msisdn;
    private Boolean isHBBProfile;
}
