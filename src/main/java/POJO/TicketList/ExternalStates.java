package POJO.TicketList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExternalStates {
    private InternalState internalState;

    private String isReopenedState;

    private String active;

    private String id;

    private String externalStateName;
}
