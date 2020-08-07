package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
