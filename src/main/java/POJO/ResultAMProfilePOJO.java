package POJO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class ResultAMProfilePOJO {
    String message;
    String firstName;
    String lastName;
    String regStatus;
    String msisdn;
    String isPinReset;
    boolean userBarred;
    boolean pinSet;
    String dob;
    ArrayList<walletPOJO> wallet;


}