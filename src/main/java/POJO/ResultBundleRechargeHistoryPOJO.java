package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultBundleRechargeHistoryPOJO {
    String packageCategory;
    String bundleName;
    String bundlePrice;
    String expiresOn;
    String status;
    String subscriptionDateTime;
    String txnNumber;
    String validity;
}
