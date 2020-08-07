package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;


@Getter
@ToString
@Setter
public class AccountsBalancePOJO {
    String message;
    String status;
    int statusCode;
    int totalCount;
    ArrayList<balanceResult> result;
    apiErrors apiErrors;
}
