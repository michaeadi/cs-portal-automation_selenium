package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfilePOJO {

    String status;
    int statusCode;
    ResultProfilePOJO result;
    apiErrors apiErrors;
}
