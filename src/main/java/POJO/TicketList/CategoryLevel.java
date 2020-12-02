package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryLevel {
    private int level;

    private int id;

    private String categoryName;
}
