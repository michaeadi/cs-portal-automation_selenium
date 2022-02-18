package com.airtel.cs.model.request.vas;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AvailableVasRequest {
    private String msisdn;
}
