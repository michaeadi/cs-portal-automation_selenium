package com.airtel.cs.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FetchTicketPool {
    List<String> ticketId;
    Boolean isSupervisor;
}
