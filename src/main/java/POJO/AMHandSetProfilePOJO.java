package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AMHandSetProfilePOJO {
    String message;
    int statusCode;
    String status;
    ResultAMHandSetProfilePOJO result;
    apiErrors apiErrors;
}
