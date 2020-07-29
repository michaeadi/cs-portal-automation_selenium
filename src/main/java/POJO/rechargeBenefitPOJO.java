package POJO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class rechargeBenefitPOJO {
    //    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonProperty("SMS")
    String SMS;
    @JsonProperty("DATA")
    String DATA;
    @JsonProperty("VOICE")
    String VOICE;
}
