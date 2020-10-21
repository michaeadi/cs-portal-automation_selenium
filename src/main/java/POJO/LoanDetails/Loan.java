package POJO.LoanDetails;

import POJO.LoanSummary.Details;
import POJO.Vendors.VendorNames;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Loan {
    private String status;
    private String statusCode;
    private VendorNames result;
    private String apiErrors;
    private String message;
}
