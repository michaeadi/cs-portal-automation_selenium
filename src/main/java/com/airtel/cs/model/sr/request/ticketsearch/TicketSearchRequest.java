package com.airtel.cs.model.sr.request.ticketsearch;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

    public  class TicketSearchRequest {
    public TicketSearchRequest(TicketSearchCriteria ticketSearchCriteria) {
        this.ticketSearchCriteria=ticketSearchCriteria;
    }

    private TicketSearchCriteria ticketSearchCriteria;

        private Integer pageNumber = 0;

        private Integer pageSize = 5;
    }



