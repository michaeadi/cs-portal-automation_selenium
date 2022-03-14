package com.airtel.cs.model.cs.request.clientconfig;

import com.airtel.cs.model.cs.response.clientconfig.ConfigResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientConfigRequest {
    private String status;
    private String statusCode;
    private ArrayList<ConfigResponse> result;
    private String message;
}
