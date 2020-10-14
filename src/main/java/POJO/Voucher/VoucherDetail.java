package POJO.Voucher;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoucherDetail {
    private String voucherId;
    private String status;
    private String subStatus;
    private String rechargeAmount;
    private String expiryDate;
    private String batchId;
    private String agent;
    private String voucherGroup;
    private String timestamp;
    private String subscriberId;
}
