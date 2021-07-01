package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pack {
    public String packId;
    public String category;
    public String subCategory;
    public String price;
    public String currency;
    public String voice;
    public String voiceUnit;
    public String data;
    public String dataUnit;
    public String sms;
}