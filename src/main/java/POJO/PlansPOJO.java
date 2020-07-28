package POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlansPOJO {

    String status;
    int statusCode;
    PlansResultPOJO result;
    apiErrors apiErrors;
}
