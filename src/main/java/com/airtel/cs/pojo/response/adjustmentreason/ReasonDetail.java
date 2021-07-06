package com.airtel.cs.pojo.response.adjustmentreason;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReasonDetail {
    private String id;
    private String action;
    private String description;
    private String reason;
    private Map<Integer,String> accountType;
    private Map<Integer,String> operation;
}
