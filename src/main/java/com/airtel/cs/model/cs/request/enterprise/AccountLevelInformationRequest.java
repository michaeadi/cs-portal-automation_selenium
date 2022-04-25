package com.airtel.cs.model.cs.request.enterprise;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountLevelInformationRequest {
    private String custMobileNo;
    private int limit;
    private int offset;
}
