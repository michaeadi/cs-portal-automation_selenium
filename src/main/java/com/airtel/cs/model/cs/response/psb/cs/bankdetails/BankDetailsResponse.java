package com.airtel.cs.model.cs.response.psb.cs.bankdetails;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankDetailsResponse {
    private String status;
    private Integer statusCode;
    private List<Result> result;
}
