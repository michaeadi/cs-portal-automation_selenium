package POJO.ClearRefillStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultResponse {
    private Boolean refilledBarred;
    private String voucherRefillUnbarDateTime;
}
