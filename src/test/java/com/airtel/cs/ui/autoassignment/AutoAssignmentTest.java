package com.airtel.cs.ui.autoassignment;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.agents.AdditionalDetails;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLog;
import com.airtel.cs.model.response.tickethistorylog.TicketHistoryLogList;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AutoAssignmentTest extends Driver {
    String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, dependsOnMethods = {"openCustomerInteraction"}, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void CreateNFTRInteraction() {
        String ticketNumber = null;
        try {
            final String issueCode = UtilsMethods.getCategoryHierarchy().get(4);
            selUtils.addTestcaseDescription("Creating NFTR issue to check Auto assignment/un-assignment,Validate ticket is created successfully or not" + issueCode, "description");
            pages.getCustomerProfilePage().clickOnInteractionIcon();
            pages.getInteractionsPage().clickOnCode();
            try {
                pages.getInteractionsPage().searchCode(issueCode);
                pages.getInteractionsPage().selectCode(issueCode);
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to select code", true);
                pages.getInteractionsPage().clickOutside();
                throw new NoSuchElementException("Not able to select code or code not found");
            }
            commonLib.info("Creating ticket with issue code -" + issueCode);
            pages.getInteractionsPage().sendComment(constants.getValue("cs.automation.comment"));
            assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isSaveEnable(), true, "Save Button Enabled Successfully", "Save Button NOT Enabled"));
            pages.getInteractionsPage().clickOnSave();
            assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Ticket Id Visible Successfully over Header", "Ticket Id NOT Visible over Header"));
            commonLib.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
            String[] valueToWrite;
            if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                commonLib.info("Ticket Number:" + ticketNumber);
                valueToWrite = new String[]{ticketNumber};
                UtilsMethods.setAutoAssignmentTicketId(valueToWrite[0]);
                commonLib.pass("Ticket Number Written into properties file " + valueToWrite[0]);
            } else {
                commonLib.fail("It's FTR not NFTR", true);
            }
            pages.getInteractionsPage().closeInteractions();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - CreateNFTRInteraction" + e.fillInStackTrace(), true);
            pages.getInteractionsPage().closeInteractions();
            pages.getInteractionsPage().clickOnContinueButton();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "CreateNFTRInteraction"})
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

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "openSupervisorDashboard", "CreateNFTRInteraction"})
    public void loginIntoTestAutomationQueue() {
        try {
            selUtils.addTestcaseDescription("Validate Agent Login into queue button display,click on login into queue button,validate queue login pop up display,Click on Test queue name", "description");
            pages.getSupervisorTicketList().clickLoginQueueButton();
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().isLoginPopup(), true, "Login into queue pop up display as expected", "Login into queue pop up does not display as expected", true));
            pages.getSupervisorTicketList().clickQueueLogin();
            pages.getSupervisorTicketList().clickContinueButton();
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().isAgentLoggedIntoQueue(), true, "Agent Login into queue as expected", "Agent does not Login into queue as expected", true));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - loginIntoTestAutomationQueue" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "openSupervisorDashboard", "CreateNFTRInteraction", "loginIntoTestAutomationQueue"})
    public void validateTicketAutoAssigned() {
        try {
            selUtils.addTestcaseDescription("Validate Agent Login into queue,Validate ticket assigned to login agent", "description");
            pages.getSupervisorTicketList().clickRefreshBtn();
            String ticketId = constants.getValue(CommonConstants.AUTO_ASSIGNMENT_TICKET_ID);
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Searched Ticket found on UI as expected", "Searched Ticket does not found on UI as expected", true));
            assertCheck.append(actions.assertEqualBoolean(pages.getSupervisorTicketList().getAssigneeAUUID().contains(loginAUUID), true, "Ticket auto assigned to Login Agent", "Ticket does not auto assigned to Login Agent", true));
            TicketHistoryLog ticketHistoryLog = api.getTicketHistoryLog(ticketId);
            final Integer statusCode = ticketHistoryLog.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Ticket History log API Status Code Matched and is :" + statusCode, "Ticket History log Status Code NOT Matched and is :" + statusCode));
            TicketHistoryLogList ticketHistoryLogList = ticketHistoryLog.getResult().getTicketInteractionComments().get(ticketHistoryLog.getResult().getTicketInteractionComments().size() - 1);
            AdditionalDetails agentDetails = UtilsMethods.getAgentDetail(new Headers(map)).getAdditionalDetails();
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getEvent(), constants.getValue(CommonConstants.AUTO_ASSIGNMENT_EVENT_NAME), "Ticket assigned to user by auto assignment", "Ticket does not assigned to user by auto assignment", true));
            assertCheck.append(actions.assertEqualIntType(ticketHistoryLogList.getAgentId(), agentDetails.getId(), "Agent id same as expected", "Agent id does not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(ticketHistoryLogList.getAssignTo(), agentDetails.getName(), "Agent name same as expected", "Agent name does not same as expected"));
            continueUnAssignment = true;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicketAutoAssigned" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


}
