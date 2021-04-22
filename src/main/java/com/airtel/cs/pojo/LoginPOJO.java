package com.airtel.cs.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginPOJO {
    String username;
    String password;
    private String message;
    private String statusCode;
    private Map<String, String> result;


    public static LoginPOJO loginBody(String username, String password) {
        LoginPOJO req = new LoginPOJO();
        req.setPassword(password);
        req.setUsername(username);
        return req;
    }
}
