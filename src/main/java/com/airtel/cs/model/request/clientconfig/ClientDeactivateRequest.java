package com.airtel.cs.model.request.clientconfig;

import com.airtel.cs.model.response.clientconfig.ConfigResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDeactivateRequest {
    private String status;
    private String statusCode;
    private ConfigResponse result;
    private String message;
}
