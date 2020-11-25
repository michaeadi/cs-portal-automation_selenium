package POJO.CRBT;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Top20Ringtone {

    private String message;
    private String status;
    private String statusCode;
    private POJO.apiErrors apiErrors;
    private int totalCount;
    private ArrayList<RingtoneResult> result;
}
