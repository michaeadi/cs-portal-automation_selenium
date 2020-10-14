package POJO.Voucher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoucherSearchPOJO {
    private String status;
    private int statusCode;
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    private VoucherDetail result;
    private String message;
}
