package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.smshistory.SMSHistory;
import com.airtel.cs.model.cs.response.smshistory.SMSHistoryList;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SupervisorUpdateTicketTest extends Driver {

    static String ticketId = null;
    final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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
            assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "ticketId", dataProviderClass = DataProviders.class, dependsOnMethods = "openSupervisorDashboard")
    public void updateTicket(NftrDataBeans Data) {
        try {
            selUtils.addTestcaseDescription("Update Ticket Id: " + Data.getIssueCode() + ",Search Ticket id which is already created,Open View Ticket Detail page,Select state which mapped to internal state 'Close'," +
                    "Click on Submit button,Validate the ticket state changed and agent able to view in closed ticket list.,Validate SMS Sent to customer initiated from application once ticket closed.", "description");
            DataProviders data = new DataProviders();
            String selectedState = null;
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
            pages.getSupervisorTicketList().writeTicketId(Data.getTicketNumber());
            pages.getSupervisorTicketList().clickSearchBtn();
            assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getTicketIdValue(), Data.getTicketNumber(), "Search Ticket does found","Search Ticket does not found",true,true));
            pages.getSupervisorTicketList().viewTicket();
            try {
                String selectStateByConfig = data.getState(constants.getValue(CommonConstants.TICKET_CLOSE_STATE)).get(0).getTicketStateName();
                selectedState = pages.getViewTicket().selectState(selectStateByConfig);
                if (selectedState.equalsIgnoreCase(selectStateByConfig)) {
                    pages.getSupervisorTicketList().enterCommentAndClickSubmit();
                    pages.getSupervisorTicketList().changeTicketTypeToClosed();
                    pages.getSupervisorTicketList().writeTicketId(Data.getTicketNumber());
                    pages.getSupervisorTicketList().clickSearchBtn();
                    assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getTicketIdValue(), Data.getTicketNumber(), "Search ticket fetched correctly", "Search Ticket Does not Fetched Correctly",true,true));
                    assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getStatevalue(), selectedState, "Ticket is Updated to the Selected State","Ticket is not Updated to the Selected State",true,true));
                    if (ticketId == null) {
                        ticketId = Data.getTicketNumber();
                    }
                    SMSHistory smsHistory = api.smsHistoryTest(customerNumber);
                    SMSHistoryList list = smsHistory.getResult().get(0);
                    commonLib.info("Message Sent after closure: " + list.getMessageText());
                    assertCheck.append(actions.assertEqualBoolean(list.getMessageText().contains(Data.getTicketNumber()), true, "Message Sent to customer for same ticket id which has been closed", "Message does not Sent for same ticket id which has been closed"));
                    assertCheck.append(actions.assertEqualStringType(list.getSmsType().toLowerCase().trim(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE).toLowerCase().trim(), "Message type is system", "Message type is not system"));
                    assertCheck.append(actions.assertEqualBoolean(list.getAction(), false, "Action button is disabled", "Action button is not disabled"));
                    assertCheck.append(actions.assertEqualStringType((list.getTemplateName().toLowerCase().trim()), constants.getValue(CommonConstants.TICKET_CLOSED_EVENT).toLowerCase().trim(), "Template event same as expected.", "Template event not same as expected."));
                } else {
                    pages.getViewTicket().clickBackButton();
                }
            } catch (AssertionError | TimeoutException | NoSuchElementException | ElementClickInterceptedException e) {
                commonLib.fail("Update Ticket does not complete due to error :" + e.fillInStackTrace(), true);
                pages.getViewTicket().clickBackButton();
            }
            pages.getSupervisorTicketList().clearInputBox();
            pages.getSupervisorTicketList().changeTicketTypeToOpen();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - updateTicket" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openSupervisorDashboard")
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
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

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openSupervisorDashboard")
    public void validateReopenIcon() {
        try {
            if (ticketId != null) {
                selUtils.addTestcaseDescription("Validate Re-open Icon on Closed Ticket: " + ticketId + ",Open View History Tab, Open Ticket History tab,Search Ticket using ticket id, Validate the reopen ticket icon on searched ticket.", "description");
                pages.getCustomerProfilePage().goToViewHistory();
                pages.getViewHistory().goToTicketHistoryTab();
                pages.getFrontendTicketHistoryPage().writeTicketId(ticketId);
                pages.getFrontendTicketHistoryPage().clickSearchBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getFrontendTicketHistoryPage().getTicketId(1), ticketId, "Ticket Id does same as search ticket id.", "Ticket Id does not same as search ticket id."));
                pages.getFrontendTicketHistoryPage().getTicketState(1);
                assertCheck.append(actions.assertEqualBoolean(pages.getFrontendTicketHistoryPage().checkReopen(1), true, "Reopen icon found on closed ticket", "Reopen icon does not found on closed ticket"));
            } else {
                commonLib.warning("No Ticket Id updated through automation to close state. SKIP Validate Re-open Icon on Closed Ticket");
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateReopenIcon" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
