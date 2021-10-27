package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.hbb.ReceiverId;
import com.airtel.cs.model.response.hbb.AlternateMsisdn;
import com.airtel.cs.model.response.hbb.HbbUserDetailsResponse;
import com.airtel.cs.model.response.hbb.NotificationServiceResponse;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class HbbSendSMSTest extends Driver {

    String templateName ,messageContent ,hbbCustomerNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest","SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest","SmokeTest"}, dependsOnMethods = "openCustomerInteraction")
    public void sendSMS() {
        try {
            selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().isPageLoaded(), true, "Send SMS tab opened correctly", "Send SMS tab does not open correctly"));
            HbbUserDetailsResponse hbbUser = api.hbbUserDetailsTest(hbbCustomerNumber);
            Assert.assertEquals(pages.getSendSMS().getCustomerNumberHbb(), String.valueOf(hbbUser.getResult().getAlternateMsisdnList()), "Alternate number is pre-filled in place of the MSISDN");
            pages.getSendSMS().selectCategory();
            templateName = pages.getSendSMS().selectTemplateName();
            pages.getSendSMS().selectLanguage();
            assertCheck.append(actions.assertEqualBoolean(pages.getSendSMS().clickSendSMSBtn(), true, "Send SMS is enabled", "Send SMS does not enabled"));
            assertCheck.append(actions.assertEqualStringType(pages.getSendSMS().getSendSMSHeaderText(), "SMS Sent", "Send SMS Header Text Matched", "Send SMS Header Text NOT Matched"));
            assertCheck.append(actions.assertEqualStringType(pages.getSendSMS().getSMSModalText(), "Message sent successfully to " + hbbCustomerNumber, "Send SMS Success Message is Correct", "Sens SMS Success Message is NOT Correct"));
            pages.getSendSMS().clickOutside();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - sendSMS" + e.fillInStackTrace(), true);
            pages.getSendSMS().clickOutside();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to Check Sent SMS display in message history
     */
    @Test(priority = 3, groups = {"SanityTest", "ProdTest","RegressionTest"}, dependsOnMethods = "openCustomerInteraction")
    public void checkSendMessageLog() {
        try {
            selUtils.addTestcaseDescription("Check Sent SMS display in message history ", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnMessageHistory();
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is displayed on UI", "Message Type Column does not display on UI"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isDateSentColumn(), true, "Date Sent Column is displayed on UI", "Date Sent Column does not display on UI"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isTemplateColumn(), true, "Template/Event Column is displayed on UI", "Template/Event Column does not display on UI"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isMessageLanguageColumn(), true, "Message Language Column is displayed on UI", "Message Language Column does not display on UI"));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isMessageTextColumn(), true, "Message Text Column is displayed on UI", "Message Text Column does not display on UI"));
            assertCheck.append(actions.assertEqualStringNotNull(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), constants.getValue(CommonConstants.MANUAL_SMS_TYPE).toLowerCase().trim(), "Message Type is NOT manual"));
            assertCheck.append(actions.assertEqualStringNotNull(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template."));
            messageContent = pages.getMessageHistoryPage().messageText(1);
            assertCheck.append(actions.assertEqualStringNotNull(messageContent, "Message content is not empty.", "Message content can not be empty."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isActionBtnEnable(1), true, "Resend SMS icon does not enable"));
            assertCheck.append(actions.assertEqualBoolean(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, "Agent id must is not empty", "Agent id must not be empty"));
            assertCheck.append(actions.assertEqualBoolean(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, "Agent name is not empty", "Agent name must not be empty"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkSendMessageLog " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to Re-Send SMS using action button in message history
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"checkSendMessageLog", "openCustomerInteraction"})
    public void reSendMessageLog() {
        try {
            selUtils.addTestcaseDescription("Re Send SMS using action button in message history", "description");
            pages.getMessageHistoryPage().clickActionBtn(1);
            final String popUpTitle = pages.getMessageHistoryPage().getPopUpTitle();
            assertCheck.append(actions.assertEqualStringType(popUpTitle, "Resend Message", "Pop up message matched successully", "Pop up message NOT matched and is: " + popUpTitle));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().getPopUpMessage().contains(hbbCustomerNumber), true, "Pop up Message tab contain customer number", "Pop up Message tab does not contain customer number"));
            pages.getMessageHistoryPage().clickYesBtn();
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is displayed on UI", "Message Type Column does not display on UI"));
            assertCheck.append(actions.assertEqualStringNotNull(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), constants.getValue(CommonConstants.MANUAL_SMS_TYPE).toLowerCase().trim(), "Message Type is NOT manual"));
            assertCheck.append(actions.assertEqualStringNotNull(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template."));
            assertCheck.append(actions.assertEqualStringNotNull(pages.getMessageHistoryPage().messageText(1).toLowerCase().trim(), messageContent.toLowerCase().trim(), "Message content is same as set message content."));
            assertCheck.append(actions.assertEqualBoolean(pages.getMessageHistoryPage().isActionBtnEnable(1), true, "Resend SMS icon does not enable"));
            assertCheck.append(actions.assertEqualBoolean(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, pages.getMessageHistoryPage().agentId(1) + " :Agent id must not be empty"));
            assertCheck.append(actions.assertEqualBoolean(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, pages.getMessageHistoryPage().agentName(1) + " :Agent name must not be empty"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - reSendMessageLog" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testManualSMS() {
        try {
            selUtils.addTestcaseDescription(
                    "Validating parameters in notification service API", "description");
            HbbUserDetailsResponse hbbUser = api.hbbUserDetailsTest(hbbCustomerNumber);
            String searchId=hbbCustomerNumber;
            List<AlternateMsisdn> receiverIds=null;
             receiverIds=hbbUser.getResult().getAlternateMsisdnList();
            NotificationServiceResponse notificationService= api.getNotificationService("1_GGCS6Mrd_1632734159995","test", "FR",searchId,"SMS","Customer_Service","SMS",receiverIds);
            final int statusCode = notificationService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Notification Service API  Status Code Matched and is :" + statusCode, "Notification Service API  Status Code Matched " + statusCode, false));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testHbbProfile " + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}


