package com.airtel.cs.model.cs.response.psb.cs.fetchbalance;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    public String currency;
    public String balance;
    public String fundsInClearance;
    public String frozenAmt;
}
