package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
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