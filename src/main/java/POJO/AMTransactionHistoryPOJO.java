package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
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
