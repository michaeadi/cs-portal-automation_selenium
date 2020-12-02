package POJO.ClearRefillStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefillStatus {
    private String message;
    private String status;
    private String statusCode;
    private ResultResponse response;
}

/*"message": "Success",
        "status": "SUCCESS",
        "statusCode": 200,
        "response": {
        "refilledBarred": true,
        "voucherRefillUnbarDateTime": "20201111T01:48:43+0300"
        }*/