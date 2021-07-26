package com.airtel.cs.model.response.login;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Login {
    private String username;
    private String password;
    private String message;
    private String statusCode;
    private Map<String, String> result;


    public static Login loginBody(String username, String password) {
        Login req = new Login();
        req.setPassword(password);
        req.setUsername(username);
        return req;
    }
}
