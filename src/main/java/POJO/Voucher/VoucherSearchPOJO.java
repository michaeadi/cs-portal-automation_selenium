package POJO.Voucher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherSearchPOJO {
    private String status;
    private int statusCode;
    private VoucherDetail result;
    private String message;
}
