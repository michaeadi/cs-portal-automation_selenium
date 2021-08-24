package com.airtel.cs.model.response.consolelog;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkParameter {
    public List<Object> associatedCookies;
    public ClientSecurityState clientSecurityState;
    public NetworkHeaders headers;
    public String requestId;
}
