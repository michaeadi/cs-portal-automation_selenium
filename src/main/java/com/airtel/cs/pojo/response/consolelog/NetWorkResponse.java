package com.airtel.cs.pojo.response.consolelog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetWorkResponse {
    public String method;
    public NetworkParameter params;
}
