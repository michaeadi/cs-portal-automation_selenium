package POJO.LoanDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanRepaymentList {
    private String id;
    private Double amountCredited;
    private Double serviceCharge;
    private Double recovered;
    private String loanType;
    private String dateCreated;
    private String loanChannel;
    private LoanRepaymentTransaction loanRepaymentTransaction;
}
