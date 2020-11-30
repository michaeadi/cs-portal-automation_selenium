package POJO.CRBT;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RingtoneResult {
    private String name;
    private String singer;
    private String renewalAmount;
    private String period;
    private String tuneId;
    private Boolean isHelloTunesSubscribed;
    private String message;
}
