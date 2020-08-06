package POJO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AMTransactionHistoryPOJO {
    String status;
    String message;
    int statusCode;
    int pageSize;
    int pageNumber;
    int totalCount;
    ArrayList<AMTransactionResults> result;
    apiErrors apiErrors;
}
