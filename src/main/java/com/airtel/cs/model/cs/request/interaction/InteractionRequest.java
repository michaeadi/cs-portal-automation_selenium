package com.airtel.cs.model.cs.request.interaction;

import com.airtel.cs.model.cs.response.interaction.InteractionResult;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionRequest {
    private String status;
    private String statusCode;
    private InteractionResult result;
    private String message;
}
