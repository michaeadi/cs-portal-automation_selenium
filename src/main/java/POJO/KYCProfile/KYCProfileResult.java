package POJO.KYCProfile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class KYCProfileResult {

    private String activationDate;
    private String sim;
    private String simType;
    private String status;
    private String lineType;
    private String serviceCategory;
    private String segment;
    private String subSegment;
    private String serviceClass;
    private Boolean vip;
    private ArrayList<PUKPOJO> puk;
}
