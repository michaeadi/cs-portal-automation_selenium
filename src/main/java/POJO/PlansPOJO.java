package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class PlansPOJO {

    String status;
    int statusCode;
    PlansResultPOJO result;
    apiErrors apiErrors;
}
