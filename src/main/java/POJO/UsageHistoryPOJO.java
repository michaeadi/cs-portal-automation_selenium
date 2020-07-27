package POJO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UsageHistoryPOJO {

    String status;
    int statusCode;
    int pageSize;
    int pageNumber;
    int totalCount;
    ArrayList<ResultUsageHistoryPOJO> result;
    apiErrors apiErrors;
}
