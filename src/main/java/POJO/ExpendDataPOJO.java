package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpendDataPOJO {
    private String type;
    private String dateTime;
    private String startBalance;
    private String charges;
    private String endBalance;
    private String bundleName;
    private String time;
}