package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.driver.Driver;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class InteractionCommentTest extends Driver {

    String ticketNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
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
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dataProvider = "interactionComment", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void addInteractionComment(NftrDataBeans data) {
        try {
            final String issueCode = data.getIssueCode();
            selUtils.addTestcaseDescription("Add Interaction Ticket Comment on Ticket" + issueCode, "description");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDateTime now = LocalDateTime.now();
            commonLib.info(dtf.format(now));
            pages.getCustomerProfilePage().clickOnInteractionIcon();
            pages.getInteractionsPage().clickOnCode();
            pages.getInteractionsPage().searchCode(issueCode);
            pages.getInteractionsPage().selectCode(issueCode);
            commonLib.info("Creating ticket with issue code -" + issueCode);
            commonLib.info(pages.getInteractionsPage().getIssue());
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssue().trim().toLowerCase().replace(" ", ""), data.getIssue().trim().toLowerCase().replace(" ", ""), "Issue is as expected", "Issue is NOT as expected"));
            commonLib.info(pages.getInteractionsPage().getIssueSubSubType());
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubSubType().trim().toLowerCase().replace(" ", ""), data.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), "Issue sub sub type is as expected", "Issue sub sub type is NOT as expected"));
            commonLib.info(pages.getInteractionsPage().getIssueType());
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueType().trim().toLowerCase().replace(" ", ""), data.getIssueType().trim().toLowerCase().replace(" ", ""), "Issue type is as expected", "Issue type is NOT as expected"));
            commonLib.info(pages.getInteractionsPage().getIssueSubType());
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubType().trim().toLowerCase().replace(" ", ""), data.getIssueSubType().trim().toLowerCase().replace(" ", ""), "Issue sub type is as expected", "Issue sub type is NOT as expected"));
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel1(), data.getIssueFieldType1(), data.getIssueFieldMandatory1(), "1");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel2(), data.getIssueFieldType2(), data.getIssueFieldMandatory2(), "2");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel3(), data.getIssueFieldType3(), data.getIssueFieldMandatory3(), "3");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel4(), data.getIssueFieldType4(), data.getIssueFieldMandatory4(), "4");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel5(), data.getIssueFieldType5(), data.getIssueFieldMandatory5(), "5");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel6(), data.getIssueFieldType6(), data.getIssueFieldMandatory6(), "6");
            pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel7(), data.getIssueFieldType7(), data.getIssueFieldMandatory7(), "7");
            pages.getInteractionsPage().sendComment("Automation Suite");
            Assert.assertTrue(pages.getInteractionsPage().isSaveEnable());
            pages.getInteractionsPage().clickOnSave();
            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Ticket Id is Visible", "Ticket Id is NOT Visible"));
            commonLib.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
            if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                pages.getInteractionsPage().clickCommentIcon();
                final String comment = pages.getInteractionsPage().addInteractionComment();
                pages.getInteractionsPage().saveInteractionComment();
                pages.getInteractionsPage().openAddedComment();
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAddedComment().toLowerCase().trim(), comment.toLowerCase().trim(), "Agent Added Interaction Ticket comment matched", "Agent Added Interaction Ticket comment does not matched"));
            } else {
                commonLib.fail("It's FTR not NFTR", true);
            }
        } catch (NoSuchElementException e) {
            pages.getInteractionsPage().closeInteractions();
            pages.getInteractionsPage().clickOnContinueButton();
        }
        pages.getInteractionsPage().closeTicketCommentBox();
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"addInteractionComment", "openCustomerInteraction"})
    public void checkSendMessageLog() {
        selUtils.addTestcaseDescription("Check Sent SMS display in message history for System Type", "description");
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().clickOnMessageHistory();
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is display on UI", "Message Type Column does not display on UI"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isDateSentColumn(), true, "Date Sent Column is display on UI", "Date Sent Column does not display on UI"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isTemplateColumn(), true, "Template/Event Column ist display on UI", "Template/Event Column does not display on UI"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageLanguageColumn(), true, "Message Language Column is display on UI", "Message Language Column does not display on UI"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTextColumn(), true, "Message Text Column is display on UI", "Message Text Column does not display on UI"));
        assertCheck.append(actions.assertEqual_stringType(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE).toLowerCase().trim(), "Message Type is system", "Message Type is not system"));
        assertCheck.append(actions.assertEqual_stringType(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().messageText(1).contains(ticketNumber), true, "Message content is same as set message content", "Message content not same as set message content"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isActionBtnDisable(1), true, "Resend SMS icon is disable", "Resend SMS icon does not disable"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, "Agent id is empty", "Agent id does not empty"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, "Agent name is empty", "Agent name does not empty"));
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
