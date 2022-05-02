package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FrontendAgentTicketTest extends Driver {

    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     */
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Validate Ticket Meta Data for Frontend Agent
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateTicket() {
        try {
            selUtils.addTestcaseDescription("Validate Ticket Meta Data for Frontend Agent", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().goToTicketHistoryTab();
            Integer statusCode = api.getTicketHistoryStatusCode(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            if (statusCode != 200) {
                commonLib.fail(constants.getValue("cs.get.ticket.history.api.error"), false);
                if (statusCode == 500) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getFrontendTicketHistoryPage().isUnableToFetch(),true, constants.getValue("ticket.history.error.widget.visible"),constants.getValue("ticket.history.error.widget.not.visible"),true));
                }
            } else {
                String ticketId = pages.getFrontendTicketHistoryPage().getTicketId(1);
                pages.getFrontendTicketHistoryPage().writeTicketId(ticketId);
                pages.getFrontendTicketHistoryPage().clickSearchBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getFrontendTicketHistoryPage().getTicketId(1), ticketId, "Ticket Id same as search ticket id.", "Ticket Id does not same as search ticket id."));
                assertCheck.append(actions.assertEqualStringNotNull(pages.getFrontendTicketHistoryPage().getTicketPriority(1), "Ticket priority is not null.", "Ticket priority must not be null."));
                assertCheck.append(actions.assertEqualStringNotNull(pages.getFrontendTicketHistoryPage().getTicketQueue(1), "Ticket Queue is not null.", "Ticket Queue must not be null."));
                assertCheck.append(actions.assertEqualStringNotNull(pages.getFrontendTicketHistoryPage().getSourceApp(1), "Source App is not empty.", "Source App can not be empty."));
                if (ticketId != null)
                    actions.assertAllFoundFailedAssert(assertCheck);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateTicket()" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to Validate Add to Interaction Icon on Each Ticket
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateAddToInteraction() {
        try {
            selUtils.addTestcaseDescription("Validate Add to Interaction Icon on Each Ticket", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().goToTicketHistoryTab();
            Integer statusCode = api.getTicketHistoryStatusCode(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            if (statusCode != 200) {
                commonLib.fail(constants.getValue("cs.get.ticket.history.api.error"), false);
                if (statusCode == 500) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getFrontendTicketHistoryPage().isUnableToFetch(),true, constants.getValue("ticket.history.error.widget.visible"),constants.getValue("ticket.history.error.widget.not.visible"),true));
                }
            } else {
                final boolean interactionIcon = pages.getFrontendTicketHistoryPage().validateAddToInteractionIcon();
                assertCheck.append(actions.assertEqualBoolean(interactionIcon, true, "Add to interaction Icon found on ticket", "Add to interaction Icon does not found on ticket"));
                if (!interactionIcon) continueExecutionFA = false;
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAddToInteraction()" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to Validate NFTR issue have ticket icon
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateNFTRIssue() {
        try {
            selUtils.addTestcaseDescription("Validate NFTR issue have ticket icon", "description");
            pages.getViewHistory().clickOnInteractionsTab();
            final boolean clickOnTicketIcon = pages.getViewHistory().clickOnTicketIcon();
            assertCheck.append(actions.assertEqualBoolean(clickOnTicketIcon, true, "NFTR issue found in interaction history tab", "No NFTR issue found in interaction history tab"));
            pages.getViewHistory().clickCloseTicketTab();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateNFTRIssue()" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to validate send SMS tab
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        try {
            selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
            pages.getCustomerProfilePage().clickOnAction();
            pages.getCustomerProfilePage().openSendSMSTab();
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isPageLoaded(), true, "Send SMS tab open correctly", "Send SMS tab does not open correctly",true));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isCategory(), true, "Category field displayed", "Category field does not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isCustomerNumber(), true, "Customer number displayed", "Customer number does not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isLanguage(), true, "Language field displayed", "Language field does not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isTemplateName(), true, "Template name field display", "Template name field does not display"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isMessageContentEditable(), true, "Message Content Editable", "Message Content is not Editable"));
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isSendBtnDisabled(), true, "Send SMS button is clickable", "Send SMS button is not clickable"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSendSMSTab()" + e.fillInStackTrace(), true);
        }
    }
}
