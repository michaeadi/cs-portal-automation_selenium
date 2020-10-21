package POJO.LoanSummary;

import POJO.Vendors.HeaderList;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutStanding {
    private Double value;
    private HeaderList headerList;
}
