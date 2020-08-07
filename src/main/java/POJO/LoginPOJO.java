package POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginPOJO {
    String username;
    String password;


    public static LoginPOJO loginBody(String password, String username) {
        LoginPOJO Req = new LoginPOJO();
        Req.setPassword(password);
        Req.setUsername(username);
        return Req;
    }
}
