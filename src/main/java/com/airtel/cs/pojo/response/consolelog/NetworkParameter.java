package com.airtel.cs.pojo.response.consolelog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkParameter {
    public List<Object> associatedCookies;
    public ClientSecurityState clientSecurityState;
    public NetworkHeaders headers;
    public String requestId;
}
