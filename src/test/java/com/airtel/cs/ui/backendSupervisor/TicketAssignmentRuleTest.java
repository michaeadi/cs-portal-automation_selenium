package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.rulefile.AssignmentQueueRuleDataBeans;
import com.airtel.cs.commonutils.dataproviders.rulefile.SLARuleFileDataBeans;
import com.airtel.cs.driver.Driver;
import org.testng.annotations.Test;

import java.util.List;
import java.util.PriorityQueue;

public class TicketAssignmentRuleTest extends Driver {
    private final BaseActions actions = new BaseActions();
    public static AssignmentQueueRuleDataBeans text;
    public static SLARuleFileDataBeans textSLA;

    @Test(priority = 1, dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void testTicket(NftrDataBeans data){
        try {
            DataProviders dataProviders = new DataProviders();
            PriorityQueue<AssignmentQueueRuleDataBeans> rules = dataProviders.getQueueRuleBasedOnCode("COM001");
            text = UtilsMethods.getAssignmentRule(rules, data);
            assert text != null;
            System.out.println(text.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test(priority = 2, dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void testTicketSLA(NftrDataBeans data){
        try {
            DataProviders dataProviders = new DataProviders();
            List<SLARuleFileDataBeans> rules = dataProviders.getSLARuleBasedOnCode("COM001");
            textSLA = UtilsMethods.getSLACalculationRule(rules, data);
            assert textSLA != null;
            System.out.println(textSLA.toString());
            System.out.println(UtilsMethods.setAllCustomerAttribute("731508274","123456789").toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
