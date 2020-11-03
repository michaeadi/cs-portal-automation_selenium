package POJO.Vendors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorNames {
    private String status;
    private String statusCode;
    private Result result;
    private ApiErrors apiErrors;
    private Integer totalCount;
}
