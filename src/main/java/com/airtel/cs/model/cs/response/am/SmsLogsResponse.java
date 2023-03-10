package com.airtel.cs.model.cs.response.am;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsLogsResponse {
    private String message;
    private String status;
    private Integer statusCode;
    private List<SmsLogsResult> result;
}
