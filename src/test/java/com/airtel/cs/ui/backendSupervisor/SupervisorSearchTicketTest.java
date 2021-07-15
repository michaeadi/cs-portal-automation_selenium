package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.AssignmentQueueRuleDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.SLARuleFileDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.ticketlist.IssueDetails;
import com.airtel.cs.pojo.response.ticketlist.TicketPOJO;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

@Log4j2
public class SupervisorSearchTicketTest extends Driver {

    private final DataProviders dataProviders = new DataProviders();
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!(continueExecutionBS && continueExecutionFA)) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateSupervisorTicketListMetaData() {
        try {
            selUtils.addTestcaseDescription("Validate supervisor tickets Meta Data,Validate that supervisor able to see the ticket list" +
                    ",Validate able to see the ticket meta data 'Ticket Number' 'Workgroup Name' 'Ticket Priority' 'Ticket State' 'Ticket Creation date' 'Ticket Queue' 'Category hierarchy [Issue->Issue Type->Issue Sub Type->Issue Sub Sub Type->Category Code]'", "description");
            if (pages.getSupervisorTicketList().isTicketIdLabel()) {
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTicketIdLabel(), true, "Ticket Meta data have Ticket Id field displayed", "Ticket meta data does not have Ticket Id displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isWorkGroupName(), true, "Ticket Meta data have WorkGroup field displayed", "Ticket meta data does not have WorkGroup displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isPrioritylabel(), true, "Ticket Meta data have Ticket Priority field displayed", "Ticket meta data does not have Ticket Priority displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isStateLabel(), true, "Ticket Meta data have Ticket State field displayed", "Ticket meta data does not have Ticket State displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCreationDateLabel(), true, "Ticket Meta data have Ticket Creation date field displayed", "Ticket meta data does not have Ticket Creation date displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCreatedByLabel(), true, "Ticket Meta data have Ticket Created By field displayed", "Ticket meta data does not have Ticket Created By displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isQueueLabel(), true, "Ticket Meta data have Ticket Queue field displayed", "Ticket meta data does not have Ticket Queue displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isIssueLabel(), true, "Ticket Meta data have Ticket Issue field displayed", "Ticket meta data does not have Ticket Issue displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isIssueTypeLabel(), true, "Ticket Meta data have Ticket Issue Type field displayed", "Ticket meta data does not have Ticket Issue Type displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSubTypeLabel(), true, "Ticket Meta data have Ticket Issue Sub Type field displayed", "Ticket meta data does not have Ticket Issue Sub Type displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSubSubTypeLabel(), true, "Ticket Meta data have Ticket Issue Sub Sub Type field displayed", "Ticket meta data does not have Ticket Issue Sub Sub Type displayed"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCodeLabel(), true, "Ticket Meta data have Ticket Ticket Category Code field displayed", "Ticket meta data does not have Ticket Category Code displayed"));
            } else {
                commonLib.warning("Ticket List does not display.", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSupervisorTicketListMetaData" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 3, dataProvider = "ticketId", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void supervisorSearchTicket(NftrDataBeans data) {
        try {
            selUtils.addTestcaseDescription("Search Ticket & Validate Ticket Meta Data: " + data.getTicketNumber() + ",Validate that supervisor able to see the ticket list and able to search ticket using ticket id" +
                    ",Validate ticket creation done as per rule config 'Workgroup Name' 'Ticket Priority' 'Ticket State' 'Ticket Queue' 'Category hierarchy [Issue->Issue Type->Issue Sub Type->Issue Sub Sub Type->Category Code]' all the field set as per config," +
                    "Validate total committed SLA as per config,Validate SLA for each workgroup of ticket as per config rule.,Validate Customer MSISDN Display correctly.,Check Red symbol display for negative SLA or Check Green Symbol display for positive SLA,Validate ticket layout fetch as per config", "description");
            AssignmentQueueRuleDataBeans assignmentRule=UtilsMethods.getAssignmentRule(dataProviders.getQueueRuleBasedOnCode(data.getIssueCode()),data);
            SLARuleFileDataBeans slaRule = UtilsMethods.getSLACalculationRule(dataProviders.getSLARuleBasedOnCode(data.getIssueCode()), data);
            commonLib.pass("AssignMent Rule Found: "+assignmentRule.toString());
            commonLib.pass("SLA Rule Found: "+slaRule.toString());
            try {
                pages.getSupervisorTicketList().writeTicketId(data.getTicketNumber());
                pages.getSupervisorTicketList().clickSearchBtn();
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getSupervisorTicketList().getTicketIdValue(), data.getTicketNumber(), "Searched Ticket found on UI as expected","Searched Ticket does not found on UI as expected",true));
                TicketPOJO ticketPOJO = api.ticketMetaDataTest(data.getTicketNumber());
                assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getQueueValue(), assignmentRule.getQueueName(),
                        "Ticket Queue field value same as expected " + assignmentRule.getQueueName(), "Ticket Queue field value not same as expected " + assignmentRule.getQueueName()));
                assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getPriorityValue(), assignmentRule.getTicketPriority(),
                        "Ticket Priority field value same as expected " + assignmentRule.getTicketPriority(), "Ticket Queue field value not same as expected " + assignmentRule.getTicketPriority()));
                assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getStatevalue(), assignmentRule.getTicketState(),
                        "Ticket state field value same as expected " + assignmentRule.getTicketState(), "Ticket state field value not same as expected " + assignmentRule.getTicketState()));
                assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getWorkGroupName(), slaRule.getWorkgroup1(),
                        "Ticket Workgroup field value same as expected " + slaRule.getWorkgroup1(), "Ticket Workgroup field value not same as expected " + slaRule.getWorkgroup1()));
                assertCheck.append(actions.assertEqual_stringType(UtilsMethods.convertToHR(ticketPOJO.getResult().getCommittedSla()), slaRule.getCommittedSLA(), "Ticket Committed SLA configured Correctly", "Ticket Committed SLA does not configured Correctly"));
                assertCheck.append(actions.assertEqual_stringType(pages.getSupervisorTicketList().getMSISDN().trim(), ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is same as expected", "User MSISDN is not same as expected"));
                Map<String, Long> sla = ticketPOJO.getResult().getSla();
                Map<String, String> workGroups = UtilsMethods.getWorkGroups(slaRule);
                pages.getSupervisorTicketList().compareWorkgroupName(sla, workGroups);
                List<IssueDetails> ticketLayout = ticketPOJO.getResult().getTicketDetails();
                List<String> configTicketLayout = dataProviders.getTicketLayout(data.getIssueCode());
                pages.getSupervisorTicketList().compareTicketLayout(ticketLayout, configTicketLayout);
            } catch (TimeoutException | NoSuchElementException | AssertionError e) {
                e.printStackTrace();
                commonLib.warning("Ticket id search not done for following error: " + e.getMessage(), true);
            }
            pages.getSupervisorTicketList().clearInputBox();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - supervisorSearchTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openSupervisorDashboard"})
    public void validateCheckBox() {
        try {
            selUtils.addTestcaseDescription("Validate Check Box,Validate Assign to Agent and Transfer to Queue Option on Open Ticket", "description");
            pages.getSupervisorTicketList().clickCheckbox();
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isAssignToAgent(), true, "User have option to perform action assign to agent", "User does not have option to perform action assign to agent"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTransferToQueue(), true, "User have option to perform action Transfer to Queue ", "User does not have option to perform action Transfer to Queue"));
            pages.getSupervisorTicketList().clickCheckbox();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateCheckBox" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
