package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExpendDataPOJO {
    private String type;
    private String dateTime;
    private String startBalance;
    private String charges;
    private String endBalance;
    private String bundleName;
    private String time;
}