package com.airtel.cs.model.cs.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RingtonDetailsRequest {
    private String msisdn;
    private String searchBy;
    private String searchText;
}
