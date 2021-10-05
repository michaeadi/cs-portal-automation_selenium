package com.airtel.cs.model.response.interaction;

import com.airtel.cs.model.request.interaction.ClientInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionResult {
    private String interactionId;
    private Boolean existingCustomer;
    private String createdBy;
    private String createdOn;
    private String updatedBy;
    private String updatedOn;
    private ArrayList<ClientInfo> clientInfo;
    private Boolean finalSubmit;
}
