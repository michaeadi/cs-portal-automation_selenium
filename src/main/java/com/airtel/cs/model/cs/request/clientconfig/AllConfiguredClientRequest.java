package com.airtel.cs.model.cs.request.clientconfig;

import com.airtel.cs.model.cs.response.clientconfig.AllConfiguredClientRespose;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllConfiguredClientRequest {
    private String status;
    private String statusCode;
    private ArrayList<AllConfiguredClientRespose> result;
    private String message;
}
