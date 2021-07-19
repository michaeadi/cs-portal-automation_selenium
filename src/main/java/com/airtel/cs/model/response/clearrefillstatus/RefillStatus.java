package com.airtel.cs.model.response.clearrefillstatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RefillStatus {
    private String message;
    private String status;
    private String statusCode;
    private ResultResponse response;
}

/*"message": "Success",
        "status": "SUCCESS",
        "statusCode": 200,
        "response": {
        "refilledBarred": true,
        "voucherRefillUnbarDateTime": "20201111T01:48:43+0300"
        }*/