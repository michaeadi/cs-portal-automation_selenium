package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class ResultProfilePOJO {
    String activationDate;
    String sim;
    String simType;
    String status;
    String lineType;
    String serviceCategory;
    String segment;
    String subSegment;
    String serviceClass;
    ArrayList<PUKPOJO> puk;

}