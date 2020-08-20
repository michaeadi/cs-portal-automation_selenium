package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueStates {

    private InternalState internalState;

    private boolean isReopenedState;

    private boolean active;

    private int id;

    private String externalStateName;
}
