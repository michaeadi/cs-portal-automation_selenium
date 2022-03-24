package com.airtel.cs.model.cs.response.am;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SmsLogsResult {
    private String transactionId;
    private String smsBody;
    private String smsDate;
    private String smsId;
}
