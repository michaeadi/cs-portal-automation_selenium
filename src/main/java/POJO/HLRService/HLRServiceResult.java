package POJO.HLRService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class HLRServiceResult {
    private String serviceName;
    private String serviceDesc;
    private ArrayList<String> hlrCodes;
    private ArrayList<String> hlrCodeDetails;
    private String serviceStatus;
    private String type;
}
