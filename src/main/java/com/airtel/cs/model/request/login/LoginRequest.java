package com.airtel.cs.model.request.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRequest {
    String username;
    String password;
    private String message;
    private String statusCode;
    private Map<String,String> result;


    public static LoginRequest loginBody(String username, String password) {
        LoginRequest req = new LoginRequest();
        req.setPassword(password);
        req.setUsername(username);
        return req;
    }
}
