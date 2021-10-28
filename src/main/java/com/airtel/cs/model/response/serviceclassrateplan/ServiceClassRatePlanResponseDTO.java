package com.airtel.cs.model.response.serviceclassrateplan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ServiceClassRatePlanResponseDTO {

  private String status;
  private String message;
  private ServiceClassRatePlanResponse response;
  private String timestamp;
}
