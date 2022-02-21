package com.airtel.cs.model.request.vas;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActiveVasRequest {
    private String msisdn;
    private Boolean activeVAS;
}
