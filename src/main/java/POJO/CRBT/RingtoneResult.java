package POJO.CRBT;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RingtoneResult {
    private String name;
    private String singer;
    private String renewalAmount;
    private String period;
    private String tuneId;
    private Boolean isHelloTunesSubscribed;
    private String message;
}
