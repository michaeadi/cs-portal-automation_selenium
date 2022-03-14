package com.airtel.cs.model.cs.request.openapi.interactionissue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionChannel implements Serializable {

  private static final long serialVersionUID = -6743226147015434717L;

  private Long id;

  private String name;

  private boolean isActive;

}
