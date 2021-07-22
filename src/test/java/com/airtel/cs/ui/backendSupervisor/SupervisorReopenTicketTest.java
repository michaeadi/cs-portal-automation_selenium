package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.AssignmentQueueRuleDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.SLARuleFileDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.ticketlist.TicketPOJO;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorReopenTicketTest extends Driver {

    RequestSource api = new RequestSource();
    NftrDataBeans ticketMetaData=null;
    DataProviders dataProviders = new DataProviders();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openSupervisorDashboard"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void reopenTicket() {
        try {
            selUtils.addTestcaseDescription("Validate Reopen Ticket as Supervisor,Select ticket from closed ticket list,validate reopen comment box pop up open,Add reopen ticket and click on submit button,Validate ticket is re-opened successfully.", "description");
            Object[][] list = dataProviders.getTestData5();
            assertCheck.append(actions.assertEqualBoolean(list.length>0,true,"Ticket Id found in excel sheet","No Ticket id found in excel sheet",true));
            ticketMetaData = (NftrDataBeans) list[0][0];
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            try {
                pages.getSupervisorTicketList().writeTicketId(ticketMetaData.getTicketNumber());
                pages.getSupervisorTicketList().clickSearchBtn();
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getSupervisorTicketList().getTicketIdValue(), ticketMetaData.getTicketNumber(), "Searched Ticket found on UI as expected", "Searched Ticket does not found on UI as expected", true));
                pages.getSupervisorTicketList().clickCheckbox();
                assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().isReopenBtn(), true, "Reopen button available after clicking checkbox", "Reopen button does not available after clicking checkbox",true));
                try {
                    pages.getSupervisorTicketList().clickReopenButton();
                    pages.getSupervisorTicketList().addReopenComment("Reopen Comment Using Automation");
                    pages.getSupervisorTicketList().submitReopenReq();
                    pages.getSupervisorTicketList().changeTicketTypeToOpen();
                    pages.getSupervisorTicketList().writeTicketId(ticketMetaData.getTicketNumber());
                    pages.getSupervisorTicketList().clickSearchBtn();
                    assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getStatevalue().toLowerCase().trim(), dataProviders.isReOpenState().get(0).getTicketStateName().toLowerCase().trim(), "Ticket Reopen in Correct state", "Ticket Does Not Reopen in Correct state"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Not able to add Re open comment " + e.fillInStackTrace(), true);
                    pages.getSupervisorTicketList().closedReopenBox();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.warning("No Ticket Found with closed State " + e.fillInStackTrace(), true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - reopenTicket" + e.fillInStackTrace(), true);
            pages.getSupervisorTicketList().clearInputBox();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3,dependsOnMethods = {"openSupervisorDashboard","reopenTicket"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void validateSLAAfterReopen(){
        try{
        selUtils.addTestcaseDescription("Validate SLA reset once ticket is ticket is re-open and new config must be applied,Validate Queue name reset,Validate Priority name reset,Validate workgroup name reset,Validate Workgroup SLA reset once ticket is reopen.", "description");
        TicketPOJO ticketPOJO = api.ticketMetaDataTest(ticketMetaData.getTicketNumber());
        AssignmentQueueRuleDataBeans assignmentRule= UtilsMethods.getAssignmentRule(dataProviders.getQueueRuleBasedOnCode(ticketMetaData.getIssueCode()),ticketMetaData);
        SLARuleFileDataBeans slaRule = UtilsMethods.getSLACalculationRule(dataProviders.getSLARuleBasedOnCode(ticketMetaData.getIssueCode()), ticketMetaData);
            assert assignmentRule != null;
            assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getQueueValue(), assignmentRule.getQueueName(),
                    "Ticket Queue field value same as expected " + assignmentRule.getQueueName(), "Ticket Queue field value not same as expected " + assignmentRule.getQueueName()));
            assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getPriorityValue(), assignmentRule.getTicketPriority(),
                    "Ticket Priority field value same as expected " + assignmentRule.getTicketPriority(), "Ticket Queue field value not same as expected " + assignmentRule.getTicketPriority()));
            assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getWorkGroupName(), slaRule.getWorkgroup1(),
                    "Ticket Workgroup field value same as expected" + slaRule.getWorkgroup1(), "Ticket Workgroup field value not same as expected " + slaRule.getWorkgroup1()));
            assertCheck.append(actions.assertEqualStringType(UtilsMethods.convertToHR(ticketPOJO.getResult().getWorkgroupSla()), slaRule.getSla1(), "Ticket Workgroup SLA reset as expected", "Ticket Workgroup SLA does not reset as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSLAAfterReopen" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


}
