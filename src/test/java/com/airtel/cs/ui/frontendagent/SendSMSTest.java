package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SendSMSTest extends Driver {

    String templateName = null;
    String messageContent = null;
    String customerNumber = null;
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    /**
     * This method is used to Open Customer Profile Page with valid MSISDN
     *
     * @param data
     */
    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
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

    /**
     * This method is used to validate send SMS tab
     */
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void validateSendSMSTab() {
        try {
            selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
            pages.getCustomerProfilePage();
            pages.getCustomerProfilePage().clickOnAction();
            try {
                pages.getCustomerProfilePage().openSendSMSTab();
                pages.getSendSMS().waitTillLoaderGetsRemoved();
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isPageLoaded(), true, "Send SMS tab open correctly", "Send SMS tab does not open correctly"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Send SMS tab does not open correctly" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isCategory(), true, "Category field is displayed", "Category field does not displayed"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Category field does not displayed" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isCustomerNumber(), true, "Customer number displayed", "Customer number does not displayed"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Customer number does not displayed" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isLanguage(), true, "Language field is displayed", "Language field does not displayed"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Language field does not displayed" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isTemplateName(), true, "Template name field is displayed", "Template name field does not display"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Template name field does not displayed" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isMessageContentEditable(), true, "Message Content Editable", "Message Content is not Editable"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Message Content Is Editable" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isSendBtnDisabled(), true, "Send SMS button is not clickable", "Send SMS button is clickable"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Send SMS button is display on UI" + e.fillInStackTrace(), true);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Send SMS tab does not open properly." + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateSendSMSTab" + e.fillInStackTrace(), true);
        }
    }


    /**
     * This method is used to validate send SMS tab
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "validateSendSMSTab")
    public void sendSMS() {
        try {
            selUtils.addTestcaseDescription("Validating the Send SMS Tab ", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().isPageLoaded(), true, "Send SMS tab does not open correctly"));
            Assert.assertEquals(pages.getSendSMS().getCustomerNumber(), customerNumber, "Customer Number as not same as whose profile opened");
            try {
                pages.getSendSMS().selectCategory();
                pages.getSendSMS().waitTillLoaderGetsRemoved();
                try {
                    templateName = pages.getSendSMS().selectTemplateName();
                    pages.getSendSMS().waitTillLoaderGetsRemoved();
                    try {
                        pages.getSendSMS().selectLanguage();
                        pages.getSendSMS().waitTillLoaderGetsRemoved();
                        try {
                            assertCheck.append(actions.assertEqual_boolean(pages.getSendSMS().clickSendSMSBtn(), true, "Send SMS is enabled", "Send SMS does not enabled"));
                            pages.getSendSMS().waitTillSuccessMessage();
                            pages.getSendSMS().waitTillLoaderGetsRemoved();
                        } catch (NoSuchElementException | TimeoutException e) {
                            commonLib.fail("Not able to send sms to customer." + e.fillInStackTrace(), true);
                        }
                    } catch (NoSuchElementException | TimeoutException e) {
                        commonLib.fail("Not able to select Language:" + e.fillInStackTrace(), true);
                        pages.getSendSMS().clickOutside();
                    }
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Not able to select Template Name:" + e.fillInStackTrace(), true);
                    pages.getSendSMS().clickOutside();
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to select category:" + e.fillInStackTrace(), true);
                pages.getSendSMS().clickOutside();
            }
            pages.getSendSMS().clickOutside();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - sendSMS" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Check Sent SMS display in message history
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void checkSendMessageLog() {
        try {
            selUtils.addTestcaseDescription("Check Sent SMS display in message history ", "description");
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().waitTillLoaderGetsRemoved();
            pages.getViewHistory().clickOnMessageHistory();
            pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is displayed on UI", "Message Type Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Type Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isDateSentColumn(), true, "Date Sent Column is displayed on UI", "Date Sent Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Date Sent Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isTemplateColumn(), true, "Template/Event Column is displayed on UI", "Template/Event Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Template/Event Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageLanguageColumn(), true, "Message Language Column is displayed on UI", "Message Language Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Language Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTextColumn(), true, "Message Text Column is displayed on UI", "Message Text Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Text Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is displayed on UI", "Message Type Column does not display on UI"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Type Column does not display on UI:" + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_stringNotNull(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is manual"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Message Type is not Display on UI:" + e.fillInStackTrace(), true);
            }
            try {

                assertCheck.append(actions.assertEqual_stringNotNull(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template."));
            } catch (NullPointerException e) {
                commonLib.fail("SMS is not sent, Template name is empty." + e.fillInStackTrace(), true);
            }
            try {
                messageContent = pages.getMessageHistoryPage().messageText(1);
                assertCheck.append(actions.assertEqual_stringNotNull(messageContent, "Message content is not empty.", "Message content can not be empty."));
            } catch (NullPointerException e) {
                commonLib.fail("SMS is not sent,Message Content is empty." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isActionBtnEnable(1), true, "Resend SMS icon does not enable"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Resend SMS Icon does not display on UI." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, "Agent id must is not empty", "Agent id must not be empty"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Agent Id does not display on UI." + e.fillInStackTrace(), true);
            }
            try {
                assertCheck.append(actions.assertEqual_boolean(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, "Agent name is not empty", "Agent name must not be empty"));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Agent Name does not display on UI." + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - checkSendMessageLog" + e.fillInStackTrace(), true);
        }
    }

    /**
     * This method is used to Re-Send SMS using action button in message history
     */
    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "checkSendMessageLog")
    public void reSendMessageLog() {
        try {
            selUtils.addTestcaseDescription("Re Send SMS using action button in message history", "description");
            pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
            try {
                pages.getMessageHistoryPage().clickActionBtn(1);
                pages.getMessageHistoryPage().getPopUpTitle();
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().getPopUpMessage().contains(customerNumber), true, "Pop up Message tab contain customer number", "Pop up Message tab does not contain customer number" ));
                pages.getMessageHistoryPage().clickYesBtn();
                pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isMessageTypeColumn(), true, "Message Type Column is displayed on UI", "Message Type Column does not display on UI"));
                try {
                    assertCheck.append(actions.assertEqual_stringNotNull(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), config.getProperty("manualSMSType").toLowerCase().trim(), "Message Type is not manual"));
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.fail("Message Type is not Display on UI:" + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_stringNotNull(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), templateName.toLowerCase().trim(), "Template name not same as sent template."));
                } catch (NullPointerException e) {
                    commonLib.fail("SMS is not sent, Template name is empty." + e.fillInStackTrace(), true);
                }
                try {
                    assertCheck.append(actions.assertEqual_stringNotNull(pages.getMessageHistoryPage().messageText(1).toLowerCase().trim(), messageContent.toLowerCase().trim(), "Message content is same as set message content."));
                } catch (NullPointerException e) {
                    commonLib.fail("SMS is not sent,Message Content is empty." + e.fillInStackTrace(), true);
                }

                assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isActionBtnEnable(1), true, "Resend SMS icon does not enable"));
                assertCheck.append(actions.assertEqual_boolean(!pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, pages.getMessageHistoryPage().agentId(1) + " :Agent id must not be empty"));
                assertCheck.append(actions.assertEqual_boolean(!pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, pages.getMessageHistoryPage().agentName(1) + " :Agent name must not be empty"));
            } catch (ElementClickInterceptedException | NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to resend SMS: " + e.fillInStackTrace(), true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - reSendMessageLog" + e.fillInStackTrace(), true);
        }
    }
}
