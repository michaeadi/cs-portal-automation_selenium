package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AMProfilePOJO {

    String message;
    int statusCode;
    ResultAMProfilePOJO result;
    apiErrors apiErrors;
}
