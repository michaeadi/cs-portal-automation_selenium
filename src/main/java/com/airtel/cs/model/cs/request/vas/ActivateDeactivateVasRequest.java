package com.airtel.cs.model.cs.request.vas;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivateDeactivateVasRequest {
    private String  msisdn;
    private String vendor ;
    private String  vasId;
    private String  agentId;
    private String  activate;
    private String  language;
    private String  channel;
}
