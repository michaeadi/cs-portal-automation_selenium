package com.airtel.cs.model.cs.response.plans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Pack {
    private String packId;
    private String category;
    private String subCategory;
    private String price;
    private String currency;
    private String voice;
    private String voiceUnit;
    private String data;
    private String dataUnit;
    private String sms;
}