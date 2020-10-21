package POJO.Vendors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class VendorNames {
    private String status;
    private String statusCode;
    private ArrayList<Vendors> vendors;
    private ArrayList<HeaderList> headerList;
    private String apiErrors;
    private Integer totalCount;
}
