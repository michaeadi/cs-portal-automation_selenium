package POJO.SMSHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class SMSHistoryPOJO {
    private String status;
    private int statusCode;
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    private List<SMSHistoryList> result;
    private String message;
}
