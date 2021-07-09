package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.ticketlist.IssueDetails;
import com.airtel.cs.pojo.response.ticketlist.TicketPOJO;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class SupervisorSearchTicketTest extends Driver {

    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();
    private final DataProviders dataProviders = new DataProviders();

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
            Map<String, String> workGroups = new HashMap<>();
            try {
                pages.getSupervisorTicketList().writeTicketId(data.getTicketNumber());
                pages.getSupervisorTicketList().clickSearchBtn();
                Assert.assertEquals(pages.getSupervisorTicketList().getTicketIdValue(), data.getTicketNumber(), "Search Ticket does not found");
                try {
                    TicketPOJO ticketPOJO = api.ticketMetaDataTest(data.getTicketNumber());
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getIssueValue(), data.getIssue(),
                            "Issue Field Value same as expected " + data.getIssue(), "Issue Field Value not same as expected " + data.getIssue()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getIssueTypeValue(), data.getIssueType(),
                            "Issue Type field value same as expected " + data.getIssueType(), "Issue Type field value not same as expected " + data.getIssueType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getSubTypeValue(), data.getIssueSubType(),
                            "Issue Sub Type field value same as expected " + data.getIssueSubSubType(), "Issue Sub Type field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getsubSubTypeValue(), data.getIssueSubSubType(),
                            "Issue Sub Sub Type field value same as expected " + data.getIssueSubSubType(), "Issue Sub Sub Type field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getCodeValue().toLowerCase().trim(), data.getIssueCode().toLowerCase().trim(),
                            "Issue Category Code field value same as expected " + data.getIssueSubSubType(), "Issue Category Code field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getWorkGroupName(), data.getWorkgroup1(),
                            "Ticket Workgroup field value same as expected " + data.getIssueSubSubType(), "Ticket Workgroup field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getQueueValue(), data.getAssignmentQueue(),
                            "Ticket Queue field value same as expected " + data.getIssueSubSubType(), "Ticket Queue field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.matchStringIgnoreSpecialChar(pages.getSupervisorTicketList().getPriorityValue(), data.getPriority(),
                            "Ticket Priority field value same as expected " + data.getIssueSubSubType(), "Ticket Queue field value not same as expected " + data.getIssueSubSubType()));
                    assertCheck.append(actions.assertEqual_stringType(UtilsMethods.convertToHR(ticketPOJO.getResult().getCommittedSla()), data.getCommittedSLA(), "Ticket Committed SLA configured Correctly", "Ticket Committed SLA does not configured Correctly"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getSupervisorTicketList().getMSISDN().trim(), ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is same as expected", "User MSISDN is not same as expected"));
                    Map<String, Long> sla = ticketPOJO.getResult().getSla();
                    if (data.getWorkgroup1() != null)
                        workGroups.put(data.getWorkgroup1(), data.getSla1());
                    if (data.getWorkgroup2() != null)
                        workGroups.put(data.getWorkgroup2(), data.getSla2());
                    if (data.getWorkgroup3() != null)
                        workGroups.put(data.getWorkgroup3(), data.getSla3());
                    if (data.getWorkgroup4() != null)
                        workGroups.put(data.getWorkgroup4(), data.getSla4());
                    for (Map.Entry mapElement : sla.entrySet()) {
                        String key = (String) mapElement.getKey();
                        String value = mapElement.getValue().toString();
                        log.info(key + " = " + value);
                        if (workGroups.containsKey(key)) {
                            workGroups.remove(key);
                            commonLib.pass(key + " : workgroup is configured correctly in DB as mentioned in configuration");
                        } else {
                            commonLib.fail(key + " workgroup is not configured correctly in DB as not mentioned in configuration", true);
                        }
                        if (value.charAt(0) == '-') {
                            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isNegativeSLA(), true, "For Negative SLA red symbol display", "For negative SLA red symbol does not display"));
                        } else {
                            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isPositiveSLA(), true, "For positive SLA green symbol display", "For positive SLA green symbol does not display"));
                        }
                    }
                    for (Map.Entry mapElement : workGroups.entrySet()) {
                        String key = (String) mapElement.getKey();
                        if (key != null)
                            if (!key.isEmpty())
                                commonLib.fail(key + " workgroup is not configured correctly in DB as mentioned in configuration", true);
                    }
                    ArrayList<IssueDetails> ticketLayout = ticketPOJO.getResult().getTicketDetails();
                    List<String> configTicketLayout = dataProviders.getTicketLayout(data.getIssueCode());
                    try {
                        for (IssueDetails layout : ticketLayout) {
                            if (!configTicketLayout.contains(layout.getPlaceHolder().toLowerCase().trim())) {
                                commonLib.fail(layout.getPlaceHolder() + " : Ticket Layout must not configure in database as does not mention in Config sheet.", true);
                            }
                            configTicketLayout.remove(layout.getPlaceHolder().toLowerCase().trim());
                        }
                    } catch (NullPointerException e) {
                        commonLib.info("No Ticket Layout Config in database :" + e.getMessage());
                    }

                    for (String name : configTicketLayout) {
                        commonLib.fail(name + " : Ticket Layout must be configure in database as mention in Config sheet.", true);
                    }
                } catch (NullPointerException | TimeoutException | NoSuchElementException e) {
                    e.printStackTrace();
                    commonLib.fail("Ticket meta data Assertion failed: " + e.getMessage(), true);
                }
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
