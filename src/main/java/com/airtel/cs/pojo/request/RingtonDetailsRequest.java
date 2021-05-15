package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RingtonDetailsRequest {
    String msisdn;
    String searchBy;
    String searchText;
}
