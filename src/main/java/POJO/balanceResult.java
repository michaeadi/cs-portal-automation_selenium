package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@ToString
public class balanceResult {
    String daId;
    String daDesc;
    String currentDaBalance;
    @JsonProperty("bundleType")
    String bundleType;
    long expiryDate;
}
