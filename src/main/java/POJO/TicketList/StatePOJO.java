package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatePOJO {

    private int id;
    private String externalStateName;
    private boolean isReopenedState;
    private InternalState internalState;
    private boolean active;
}

