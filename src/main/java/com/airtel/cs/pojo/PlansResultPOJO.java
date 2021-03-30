package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class PlansResultPOJO {
    lastRecharge lastRecharge;
    mainAccountBalance mainAccountBalance;
    @JsonInclude(JsonInclude.Include.USE_DEFAULTS)
    Voice voice;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Data data;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    Sms sms;
    String dataManager;
}


