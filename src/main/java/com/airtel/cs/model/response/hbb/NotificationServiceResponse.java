package com.airtel.cs.model.response.hbb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationServiceResponse {
    private String status;
    private Integer statusCode;
    private String  result;
}
