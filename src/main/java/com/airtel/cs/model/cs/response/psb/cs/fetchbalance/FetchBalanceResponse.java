package com.airtel.cs.model.cs.response.psb.cs.fetchbalance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FetchBalanceResponse {
    public String message;
    public String status;
    public int statusCode;
    public Result result;
}
