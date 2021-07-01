package com.airtel.cs.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostpaidPack {
    public String status;
    public String message;
    public Payload payload;
}