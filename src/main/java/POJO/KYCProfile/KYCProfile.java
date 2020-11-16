package POJO.KYCProfile;

import POJO.apiErrors;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KYCProfile {
    private String status;
    private String statusCode;
    private KYCProfileResult result;
    private apiErrors apiErrors;
}
