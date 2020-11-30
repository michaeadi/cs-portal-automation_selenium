package POJO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultProfilePOJO {

    private String deviceType;
    private String imeiNumber;
    private String type;
    private String brand;
    private String model;
    private String os;
    private String airtelMoneyStatus;
    private String serviceStatus;
}