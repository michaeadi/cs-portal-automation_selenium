package POJO.CRBT;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ActivateRingtone {
    private String message;
    private String status;
    private String statusCode;
    private POJO.apiErrors apiErrors;
    private int totalCount;
    private RingtoneResult result;
}
