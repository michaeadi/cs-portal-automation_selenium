package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketPOJO {
    private String status;
    private int statusCode;
    private int pageSize;
    private int pageNumber;
    private int totalCount;
    private TicketListDetails result;
    private String message;
}
