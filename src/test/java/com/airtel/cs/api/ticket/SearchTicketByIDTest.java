package com.airtel.cs.api.ticket;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.ticketdetail.TicketRequest;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Log4j2
public class SearchTicketByIDTest extends ApiPrerequisites {

    @Test(priority = 1, description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void SearchTicket(NftrDataBeans Data) {
        try {
            selUtils.addTestcaseDescription("Search Ticket & Validate Ticket Meta Data: " + Data.getTicketNumber(), "description");
            Map<String, String> workGroups = new HashMap<>();

            TicketRequest ticketPOJO = api.getTicketDetailById(validHeaderList, Data.getTicketNumber());
            assertCheck.append(actions.assertEqualIntType(ticketPOJO.getStatusCode(), 200, "Status Code Matched Succesfully", "Status code Not Matched and is - " + ticketPOJO.getStatusCode()));
            final String sourceApp = ticketPOJO.getResult().getSourceApp();
            assertCheck.append(actions.assertEqualStringNotNull(sourceApp, "Source App is not null and is -" + sourceApp, "Source App is null in the request and is -" + sourceApp));
            assertCheck.append(actions.assertEqualStringType(ticketPOJO.getResult().getQueue().getQueueName().toLowerCase().trim(), Data.getAssignmentQueue().toLowerCase().trim(),
                    "Ticket Pool Validated Successfully", "Ticket pool Does Not Validated"));
            assertCheck.append(actions.assertEqualStringType(ticketPOJO.getResult().getState().getExternalStateName().toLowerCase().trim(), Data.getState().toLowerCase().trim(),
                    "Ticket State Validated Successfully", "Ticket State Does Not Validated"));
            assertCheck.append(actions.assertEqualStringType(ticketPOJO.getResult().getPriority().getName().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                    "Ticket Priority Validated Successfully", "Ticket Priority Does Not Type Validated"));
            assertCheck.append(actions.assertEqualStringType(restUtils.convertToHR(ticketPOJO.getResult().getCommittedSla()), Data.getCommittedSLA(), "Committed SLA Matched Successfully", "Committed SLA does not configured Correctly"));
            assertCheck.append(actions.assertEqualStringNotNull(ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is same as expected", "User MSISDN is not same as expected"));
            Map<String, Long> sla = ticketPOJO.getResult().getSla();
            if (Data.getWorkgroup1() != null)
                workGroups.put(Data.getWorkgroup1(), Data.getSla1());
            if (Data.getWorkgroup2() != null)
                workGroups.put(Data.getWorkgroup2(), Data.getSla2());
            if (Data.getWorkgroup3() != null)
                workGroups.put(Data.getWorkgroup3(), Data.getSla3());
            if (Data.getWorkgroup4() != null)
                workGroups.put(Data.getWorkgroup4(), Data.getSla4());
            for (Map.Entry mapElement : sla.entrySet()) {
                String key = (String) mapElement.getKey();
                String value = mapElement.getValue().toString();
                log.info(key + " = " + value);
                if (workGroups.containsKey(key)) {
                    workGroups.remove(key);
                    commonLib.info(key + " : workgroup is configured correctly in DB as mentioned in configuration");
                } else {
                    commonLib.fail(key + " workgroup is not configured correctly in DB as not mentioned in configuration", false);
                }
            }
            for (Map.Entry mapElement : workGroups.entrySet()) {
                String key = (String) mapElement.getKey();
                String value = mapElement.getValue().toString();
                if (key != null)
                    if (!key.isEmpty())
                        commonLib.fail(key + " workgroup is not configured correctly in DB as mentioned in configuration", false);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - SearchTicket " + e.getMessage(), false);
        }
    }
}

