package POJO.HLRService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HLRServiceResult {
    private String serviceName;
    private String serviceDesc;
    private String hlrCode;
    private String serviceStatus;
}
