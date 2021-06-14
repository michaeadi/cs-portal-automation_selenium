package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.common.actions.BaseActions;
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
    private final BaseActions actions = new BaseActions();

    @BeforeMethod
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
    public void addInteractionComment(NftrDataBeans data) throws InterruptedException {
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
        try {

            final String issueFieldLabel1 = data.getIssueFieldLabel1();
            if (issueFieldLabel1 != null)
                if (data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !issueFieldLabel1.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("1"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("1").replace("*", "").trim(), (issueFieldLabel1.replace("*", "").trim()), issueFieldLabel1 + " Label matched", issueFieldLabel1 + " Label does not matched"));
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("1").contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("1", "012345");
                } else if (data.getIssueFieldType1().equalsIgnoreCase("Date") && !issueFieldLabel1.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel1, "Issue Field Label 1 is as Expected", "Issue Field Label 1 is NOT as Expected"));
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !issueFieldLabel1.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("1"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").replace("*", "").trim(), (issueFieldLabel1.replace("*", "").trim()), issueFieldLabel1 + "Label matched", issueFieldLabel1 + "Label does not matched"));
                    if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("1");
                }

            final String issueFieldLabel2 = data.getIssueFieldLabel2();
            if (issueFieldLabel2 != null)
                if (data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !issueFieldLabel2.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("2"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("2").replace("*", "").trim(), (issueFieldLabel2.replace("*", "").trim()), "Issue Field Label 2 is as Expected", "Issue Field Label 2 is NOT as Expected"));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("2").contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("2", "012345");
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Date") && !issueFieldLabel2.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel2, "Issue Field Label 2 is as Expected", "Issue Field Label 2 is NOT as Expected"));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !issueFieldLabel2.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("2"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").replace("*", "").trim(), (issueFieldLabel2.replace("*", "").trim()), "Issue Field Label 2 is as Expected", "Issue Field Label 2 is NOT as Expected"));
                    if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("2");
                }

            final String issueFieldLabel3 = data.getIssueFieldLabel3();
            if (issueFieldLabel3 != null)
                if (data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !issueFieldLabel3.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("3"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("3").replace("*", "").trim(), (issueFieldLabel3.replace("*", "").trim()), "Issue Field Label 3 is as Expected", "Issue Field Label 3 is NOT as Expected"));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("3").contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("3", "012345");
                } else if (data.getIssueFieldType3().equalsIgnoreCase("Date") && !issueFieldLabel3.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel3, "Issue Field Label 3 is as Expected", "Issue Field Label 3 is NOT as Expected"));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                } else if (data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !issueFieldLabel3.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("3"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").replace("*", "").trim(), (issueFieldLabel3.replace("*", "").trim()), "Issue Field Label 3 is as Expected", "Issue Field Label 3 is NOT as Expected"));
                    if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("3");
                }

            commonLib.info("Field Type-4" + data.getIssueFieldType4());
            final String issueFieldLabel4 = data.getIssueFieldLabel4();
            if (issueFieldLabel4 != null)
                if (data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !issueFieldLabel4.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("4"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("4").replace("*", "").trim(), (issueFieldLabel4.replace("*", "").trim()), "Issue Field Label 4 is as Expected", "Issue Field Label 4 is NOT as Expected"));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("4").contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("4", "012345");
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Date") && !issueFieldLabel4.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel4, "Issue Field Label 4 is as Expected", "Issue Field Label 4 is NOT as Expected"));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !issueFieldLabel4.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("4"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").replace("*", "").trim(), (issueFieldLabel4.replace("*", "").trim()), "Issue Field Label 4 is as Expected", "Issue Field Label 4 is NOT as Expected"));
                    if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("4");
                }

            final String issueFieldLabel5 = data.getIssueFieldLabel5();
            if (issueFieldLabel5 != null)
                if (data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !issueFieldLabel5.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("5"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("5").replace("*", "").trim(), (issueFieldLabel5.replace("*", "").trim()), "Issue Field Label 5 is as Expected", "Issue Field Label 5 is NOT as Expected"));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("5").contains("*"), true, issueFieldLabel5 + "Label is mandatory and contains '*' ", issueFieldLabel5 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("5", "012345");
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Date") && !issueFieldLabel5.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel5, "Issue Field Label 5 is as Expected", "Issue Field Label 5 is NOT as Expected"));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel5 + "Label is mandatory and contains '*' ", issueFieldLabel5 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !issueFieldLabel5.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("5"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").replace("*", "").trim(), (issueFieldLabel5.replace("*", "").trim()), "Issue Field Label 5 is as Expected", "Issue Field Label 5 is NOT as Expected"));
                    if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").contains("*"), true, issueFieldLabel5 + "Label is mandatory and contains '*' ", issueFieldLabel5 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("5");
                }

            final String issueFieldLabel6 = data.getIssueFieldLabel6();
            if (issueFieldLabel6 != null)
                if (data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !issueFieldLabel6.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("6"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("6").replace("*", "").trim(), (issueFieldLabel6.replace("*", "").trim()), "Issue Field Label 6 is as Expected", "Issue Field Label 6 is NOT as Expected"));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("6").contains("*"), true, issueFieldLabel6 + "Label is mandatory and contains '*' ", issueFieldLabel6 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("6", "012345");
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Date") && !issueFieldLabel6.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel6, "Issue Field Label 6 is as Expected", "Issue Field Label 6 is NOT as Expected"));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel6 + "Label is mandatory and contains '*' ", issueFieldLabel6 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !issueFieldLabel6.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("6"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").replace("*", "").trim(), (issueFieldLabel6.replace("*", "").trim()), "Issue Field Label 6 is as Expected", "Issue Field Label 6 is NOT as Expected"));
                    if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").contains("*"), true, issueFieldLabel6 + "Label is mandatory and contains '*' ", issueFieldLabel6 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("6");
                }

            final String issueFieldLabel7 = data.getIssueFieldLabel7();
            if (issueFieldLabel7 != null)
                if (data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !issueFieldLabel7.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("7"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("7").replace("*", "").trim(), (issueFieldLabel7.replace("*", "").trim()), "Issue Field Label 7 is as Expected", "Issue Field Label 7 is NOT as Expected"));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("7").contains("*"), true, issueFieldLabel7 + "Label is mandatory and contains '*' ", issueFieldLabel7 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setIssueDetailInput("7", "012345");
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Date") && !issueFieldLabel7.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel7, "Issue Field Label 7 is as Expected", "Issue Field Label 7 is NOT as Expected"));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel7 + "Label is mandatory and contains '*' ", issueFieldLabel7 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                } else if (data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !issueFieldLabel7.isEmpty()) {
                    commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("7"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").replace("*", "").trim(), (issueFieldLabel7.replace("*", "").trim()), "Issue Field Label 7 is as Expected", "Issue Field Label 7 is NOT as Expected"));
                    if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                        assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").contains("*"), true, issueFieldLabel7 + "Label is mandatory and contains '*' ", issueFieldLabel7 + "Label is mandatory but doesn't contains '*' "));
                    }
                    pages.getInteractionsPage().selectIssueDetailInput("7");
                }
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

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "addInteractionComment")
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
        assertCheck.append(actions.assertEqual_stringType(pages.getMessageHistoryPage().templateEvent(1).toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATE_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().messageText(1).contains(ticketNumber), true, "Message content is same as set message content", "Message content not same as set message content"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().isActionBtnDisable(1), true, "Resend SMS icon is disable", "Resend SMS icon does not disable"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().agentId(1).trim().equalsIgnoreCase("-"), true, "Agent id is empty", "Agent id does not empty"));
        assertCheck.append(actions.assertEqual_boolean(pages.getMessageHistoryPage().agentName(1).trim().equalsIgnoreCase("-"), true, "Agent name is empty", "Agent name does not empty"));
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
