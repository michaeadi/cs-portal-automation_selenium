package POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GsmKycPOJO {
    String status;
    int statusCode;
    ResultGsmKycPOJO result;
    apiErrors apiErrors;
}
