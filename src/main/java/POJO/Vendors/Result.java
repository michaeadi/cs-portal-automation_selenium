package POJO.Vendors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString
public class Result {
    private ArrayList<Vendors> vendors;
    private ArrayList<HeaderList> headerList;
}
