package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OfferDetailRequest {
    String msisdn;
    Boolean requestDADetailsFlag;
}
