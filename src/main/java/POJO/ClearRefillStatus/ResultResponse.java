package POJO.ClearRefillStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ResultResponse {
    private Boolean refilledBarred;
    private String voucherRefillUnbarDateTime;
}
