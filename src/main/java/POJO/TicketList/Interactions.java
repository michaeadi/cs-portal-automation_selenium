package POJO.TicketList;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Interactions {

    private int interactionId;

    private String issue;

    private String customerId;

    private Channel channel;

    private String disposition;

    private String interactionType;

    private String comment;

    private String createdOn;

    private int issueCount;

    private String agent;

}
