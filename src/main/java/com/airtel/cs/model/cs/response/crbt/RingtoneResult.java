package com.airtel.cs.model.cs.response.crbt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RingtoneResult {
    private String name;
    private String singer;
    private String renewalAmount;
    private String period;
    private String tuneId;
    private Boolean isHelloTunesSubscribed;
    private String message;
}
