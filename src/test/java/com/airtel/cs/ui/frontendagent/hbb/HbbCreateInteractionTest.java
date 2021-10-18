package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.hbb.HbbUserDetailsResponse;
import com.airtel.cs.model.response.smshistory.SMSHistory;
import com.airtel.cs.model.response.smshistory.SMSHistoryList;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;

public class HbbCreateInteractionTest extends Driver {

    String customerNumber = null;

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
            customerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void createInteractionSMSTest() {
        try {
            selUtils.addTestcaseDescription("Create interaction , check its entry in View History > SMS Tab", "description");
            pages.getHbbProfilePage().createNFTRInteraction();
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnMessageHistory();
            SMSHistory historyResponse = api.smsHistoryTest(customerNumber);
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
            assertCheck.append(actions.matchUiAndAPIResponse(customerNumber, historyResponse.getResult().get(0).getAlternateNumber(),
                    "Ticket Creation SMS is sent on Alternate Number", "Ticket Creation SMS is not sent on Alternate Number"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - createInteractionSMSTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

   /* @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"}dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void closeInteractionSMSTest(){
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
                        pages.getSupervisorTicketList().changeTicketTypeToClosed();
                        pages.getSupervisorTicketList().writeTicketId(Data.getTicketNumber());
                        pages.getSupervisorTicketList().clickSearchBtn();
                        assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getTicketIdValue(), Data.getTicketNumber(), "Search ticket fetched correctly", "Search Ticket Does not Fetched Correctly",true,true));
                        assertCheck.append(actions.assertEqualStringType(pages.getSupervisorTicketList().getStatevalue(), selectedState, "Ticket Does Updated to Selected State","Ticket Does not Updated to Selected State",true,true));
                        if (ticketId == null) {
                            ticketId = Data.getTicketNumber();
                        }
                        SMSHistory smsHistory = api.smsHistoryTest(customerNumber);
                        SMSHistoryList list = smsHistory.getResult().get(0);
                        commonLib.info("Message Sent after closure: " + list.getMessageText());
                        assertCheck.append(actions.assertEqualBoolean(list.getMessageText().contains(Data.getTicketNumber()), true, "Message Sent to customer for same ticket id which has been closed", "Message does not Sent for same ticket id which has been closed"));
                        assertCheck.append(actions.assertEqualStringType(list.getSmsType().toLowerCase().trim(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE).toLowerCase().trim(), "Message type is system", "Message type is not system"));
                        assertCheck.append(actions.assertEqualBoolean(list.getAction(), false, "Action button is disabled", "Action button is not disabled"));
                        assertCheck.append(actions.assertEqualStringType((list.getTemplateName().toLowerCase().trim()), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event same as expected.", "Template event not same as expected."));
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
}*/
}
