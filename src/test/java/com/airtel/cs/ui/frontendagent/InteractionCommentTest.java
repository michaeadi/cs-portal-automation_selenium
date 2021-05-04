package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class InteractionCommentTest extends Driver {

    String ticketNumber = null;
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
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

    @Test(priority = 2, description = "Create Interaction ", dataProvider = "interactionComment", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void addInteractionComment(NftrDataBeans data) throws InterruptedException {
        final String issueCode = data.getIssueCode();
        selUtils.addTestcaseDescription("Add Interaction Ticket Comment on Ticket" + issueCode, "description");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime now = LocalDateTime.now();
        log.info(dtf.format(now));
        pages.getCustomerProfilePage().clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        pages.getInteractionsPage().clickOnCode();
        try {
            pages.getInteractionsPage().searchCode(issueCode);
        } catch (Exception e) {
            Thread.sleep(1000);
            pages.getInteractionsPage().clickOnCode();
            pages.getInteractionsPage().searchCode(issueCode);

        }
        pages.getInteractionsPage().selectCode(issueCode);
        commonLib.info("Creating ticket with issue code -" + issueCode);
        log.info(pages.getInteractionsPage().getIssue());
        softAssert.assertEquals(pages.getInteractionsPage().getIssue().trim().toLowerCase().replace(" ", ""), data.getIssue().trim().toLowerCase().replace(" ", ""), "Issue is not as expected ");
        log.info(pages.getInteractionsPage().getIssueSubSubType());
        softAssert.assertEquals(pages.getInteractionsPage().getIssueSubSubType().trim().toLowerCase().replace(" ", ""), data.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), "Issue sub sub type is not as expected ");
        log.info(pages.getInteractionsPage().getIssueType());
        softAssert.assertEquals(pages.getInteractionsPage().getIssueType().trim().toLowerCase().replace(" ", ""), data.getIssueType().trim().toLowerCase().replace(" ", ""), "Issue type is not as expected ");
        log.info(pages.getInteractionsPage().getIssueSubType());
        softAssert.assertEquals(pages.getInteractionsPage().getIssueSubType().trim().toLowerCase().replace(" ", ""), data.getIssueSubType().trim().toLowerCase().replace(" ", ""), "Issue sub type is not as expected ");
        try {

            if (data.getIssueFieldLabel1() != null)
                if (data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("1"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("1").replace("*", "").trim(), (data.getIssueFieldLabel1().replace("*", "").trim()), data.getIssueFieldLabel1() + " Label does not match");
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("1").contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("1", "012345");
                } else if (data.getIssueFieldType1().equalsIgnoreCase("Date") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel1()));
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel1().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("1"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").replace("*", "").trim(), (data.getIssueFieldLabel1().replace("*", "").trim()), data.getIssueFieldLabel1() + "Label does not match");
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").contains("*"), data.getIssueFieldLabel1() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("1");
                }

            if (data.getIssueFieldLabel2() != null)
                if (data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("2"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("2").replace("*", "").trim(), (data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("2").contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("2", "012345");
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Date") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel2()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel2().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("2"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").replace("*", "").trim(), (data.getIssueFieldLabel2().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").contains("*"), data.getIssueFieldLabel2() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("2");
                }

            if (data.getIssueFieldLabel3() != null)
                if (data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("3"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("3").replace("*", "").trim(), (data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("3").contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("3", "012345");
                } else if (data.getIssueFieldType3().equalsIgnoreCase("Date") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel3()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel3().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("3"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").replace("*", "").trim(), (data.getIssueFieldLabel3().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").contains("*"), data.getIssueFieldLabel3() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("3");
                }

            log.info("Field Type-4" + data.getIssueFieldType4());

            if (data.getIssueFieldLabel4() != null)
                if (data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel4().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("4"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("4").replace("*", "").trim(), (data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("4").contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("4", "012345");
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Date") && !data.getIssueFieldLabel4().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel4()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel4().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("4"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").replace("*", "").trim(), (data.getIssueFieldLabel4().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").contains("*"), data.getIssueFieldLabel4() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("4");
                }

            if (data.getIssueFieldLabel5() != null)
                if (data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("5"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("5").replace("*", "").trim(), (data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("5").contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("5", "012345");
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Date") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel5()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel5().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("5"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").replace("*", "").trim(), (data.getIssueFieldLabel5().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").contains("*"), data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("5");
                }

            if (data.getIssueFieldLabel6() != null)
                if (data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("6"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("6").replace("*", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("6").contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("6", "012345");
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Date") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel6()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel6().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("6"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").replace("*", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").contains("*"), data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("6");
                }

            if (data.getIssueFieldLabel7() != null)
                if (data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabel("7"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabel("7").replace("*", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabel("7").contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setIssueDetailInput("7", "012345");
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Date") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().isDateFieldAvailable());
                    softAssert.assertEquals(pages.getInteractionsPage().isDateFieldAvailable(), (data.getIssueFieldLabel7()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel7().isEmpty()) {
                    log.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("7"));
                    softAssert.assertEquals(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").replace("*", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        softAssert.assertTrue(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").contains("*"), data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' ");
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("7");
                }
            pages.getInteractionsPage().sendComment("Automation Suite");
            Assert.assertTrue(pages.getInteractionsPage().isSaveEnable());
            pages.getInteractionsPage().clickOnSave();
            softAssert.assertTrue(pages.getInteractionsPage().isResolvedFTRDisplayed());
            log.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
            if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                log.info(ticketNumber);
                String comment = "Adding Interaction Comment Using Automation";
                pages.getInteractionsPage().clickCommentIcon();
                pages.getInteractionsPage().addInteractionComment(comment);
                pages.getInteractionsPage().saveInteractionComment();
                pages.getInteractionsPage().waitTillLoaderGetsRemoved();
                pages.getInteractionsPage().openAddedComment();
                softAssert.assertEquals(pages.getInteractionsPage().getAddedComment().toLowerCase().trim(), comment.toLowerCase().trim(), "Agent Added Interaction Ticket comment does not match");
            } else {
                softAssert.fail("It's FTR not NFTR");
            }
        } catch (NoSuchElementException e) {
            log.info("in catch");
            pages.getInteractionsPage().closeInteractions();
            pages.getInteractionsPage().clickOnContinueButton();
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BASE64);
        ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        pages.getInteractionsPage().closeTicketCommentBox();
        pages.getInteractionsPage().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Check Sent SMS display in message history", dependsOnMethods = "addInteractionComment")
    public void checkSendMessageLog() {
        selUtils.addTestcaseDescription("Check Sent SMS display in message history for System Type", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getCustomerProfilePage().goToViewHistory();
        pages.getViewHistory().waitTillLoaderGetsRemoved();
        pages.getViewHistory().clickOnMessageHistory();
        pages.getMessageHistoryPage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTypeColumn(), "Message Type Column does not display on UI");
        softAssert.assertTrue(pages.getMessageHistoryPage().isDateSentColumn(), "Date Sent Column does not display on UI");
        softAssert.assertTrue(pages.getMessageHistoryPage().isTemplateColumn(), "Template/Event Column does not display on UI");
        softAssert.assertTrue(pages.getMessageHistoryPage().isMessageLanguageColumn(), "Message Language Column does not display on UI");
        softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTextColumn(), "Message Text Column does not display on UI");
        softAssert.assertTrue(pages.getMessageHistoryPage().isMessageTypeColumn(), "Message Type Column does not display on UI");
        softAssert.assertEquals(pages.getMessageHistoryPage().messageType(1).toLowerCase().trim(), config.getProperty("systemSMSType").toLowerCase().trim(), "Message Type is not system");
        softAssert.assertEquals(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), config.getProperty("ticketCreateEvent").toLowerCase().trim(), "Template event not same as defined.");
        softAssert.assertTrue(pages.getMessageHistoryPage().messageText(1).contains(ticketNumber), "Message content not same as set message content.");
        softAssert.assertTrue(pages.getMessageHistoryPage().isActionBtnDisable(1), "Resend SMS icon does not disable");
        softAssert.assertTrue(pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), " :Agent id does not empty");
        softAssert.assertTrue(pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), " :Agent name does not empty");
        softAssert.assertAll();
    }
}
