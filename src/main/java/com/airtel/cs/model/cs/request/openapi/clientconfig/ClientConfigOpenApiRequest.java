package com.airtel.cs.model.cs.request.openapi.clientconfig;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientConfigOpenApiRequest {
    private String status;
    private Integer statusCode;
    private String message;
    private ArrayList<ClientConfigOpenApiResult> result;
}
