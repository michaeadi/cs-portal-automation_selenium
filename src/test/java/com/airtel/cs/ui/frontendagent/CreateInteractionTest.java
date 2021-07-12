package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.excelutils.WriteToExcel;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.smshistory.SMSHistoryList;
import com.airtel.cs.pojo.response.smshistory.SMSHistoryPOJO;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
public class CreateInteractionTest extends Driver {

    String customerNumber = null;
    RequestSource api = new RequestSource();
    private final BaseActions actions = new BaseActions();


    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqual_boolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


    @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest","ProdTest"}, dataProvider = "getTestData1", dataProviderClass = DataProviders.class)
    public void createInteraction(FtrDataBeans data) {
        try {
            final String issueCode = data.getIssueCode();
            selUtils.addTestcaseDescription(" Validating FTR Ticket: " + issueCode, "description");
            pages.getCustomerProfilePage().clickOnInteractionIcon();
            pages.getInteractionsPage().clickOnCode();
            try {
                pages.getInteractionsPage().searchCode(issueCode);
                pages.getInteractionsPage().selectCode(issueCode);
            }catch (NoSuchElementException | TimeoutException e){
                commonLib.fail("Not able to select code",true);
                pages.getInteractionsPage().clickOutside();
                throw new NoSuchElementException("Not able to select code or code not found");
            }
            commonLib.info("Creating ticket with issue code -" + issueCode);
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is as expected", "Issue is not as expected "));
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is as expected", "Issue sub sub type is not as expected "));
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is as expected", "Issue type is not as expected"));
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is as expected", "Issue sub type is not as expected "));
            pages.getInteractionsPage().sendComment("Automation Suite");
            pages.getInteractionsPage().clickOnSave();
            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Resolved FTR not display", "Resolved FTR does not display"));
            assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getResolvedFTRDisplayed(), "Resolved FTR", "Resolved FTR displayed", "Resolved FTR does not display"));
            SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
            SMSHistoryList list = smsHistory.getResult().get(0);
            commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
            //ToDo RG
            //assertCheck.append(actions.assertEqual_boolean(list.getMessageText().contains(issueCode), true, "Message sent to customer for same FTR Category for which Issue has been Created", "Message does not sent to customer for same FTR Category for which Issue has been Created"));
            assertCheck.append(actions.assertEqual_stringType(list.getSmsType(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE), "Message type is system", "Message type is not system"));
            assertCheck.append(actions.assertEqual_boolean(list.isAction(), false, "Action button is disabled", "Action button is not disabled"));
            assertCheck.append(actions.assertEqual_stringType(list.getTemplateName().toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
            pages.getInteractionsPage().closeInteractions();
        } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - createInteraction" + e.fillInStackTrace(), true);
            pages.getInteractionsPage().clickOutside();
            pages.getInteractionsPage().resetInteractionIssue();
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest","ProdTest"}, dataProvider = "getTestData2", dataProviderClass = DataProviders.class)
    public void CreateNFTRInteraction(NftrDataBeans data) {
        try {
            String ticketNumber = null;
            try {
                final String issueCode = data.getIssueCode();
                selUtils.addTestcaseDescription(" Validating NFTR Ticket: " + issueCode, "description");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDateTime now = LocalDateTime.now();
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
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is as expected", "Issue is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is as expected", "Issue sub sub type is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is as expected", "Issue type is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is as expected", "Issue sub type is not as expected"));
                final String issueFieldLabel1 = data.getIssueFieldLabel1();
                if (issueFieldLabel1 != null)
                    if (data.getIssueFieldType1().equalsIgnoreCase("Text Box") && !issueFieldLabel1.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("1"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("1").replaceAll("[^a-zA-Z]+", "").trim(), (issueFieldLabel1.replaceAll("[^a-zA-Z]+", "").trim()), issueFieldLabel1 + " Label matched", issueFieldLabel1 + " Label does not match"));
                        if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("1").contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("1", "012345");
                    } else if (data.getIssueFieldType1().equalsIgnoreCase("Date") && !issueFieldLabel1.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel1, "Label 1 Matched", "Label 1 NOT Matched"));
                        if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType1().equalsIgnoreCase("Drop Down") && !issueFieldLabel1.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("1"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").replace("*", "").trim(), (issueFieldLabel1.replace("*", "").trim()), issueFieldLabel1 + "Label matched", issueFieldLabel1 + "Label does not match"));
                        if (data.getIssueFieldMandatory1().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("1").contains("*"), true, issueFieldLabel1 + "Label is mandatory and contains '*' ", issueFieldLabel1 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("1");
                    }

                final String issueFieldLabel2 = data.getIssueFieldLabel2();
                if (issueFieldLabel2 != null)
                    if (data.getIssueFieldType2().equalsIgnoreCase("Text Box") && !issueFieldLabel2.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("2"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("2").replaceAll("[^a-zA-Z]+", "").trim(), (issueFieldLabel2.replaceAll("[^a-zA-Z]+", "").trim()), "Issue Field Label 2 matched", "Issue Field Label 2 NOT matched"));
                        if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("2").contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("2", "012345");
                    } else if (data.getIssueFieldType2().equalsIgnoreCase("Date") && !issueFieldLabel2.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel2, "Issue Field Label 2 Matched", "Issue Field Label 2 NOT Matched"));
                        if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType2().equalsIgnoreCase("Drop Down") && !issueFieldLabel2.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("2"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").replace("*", "").trim(), (issueFieldLabel2.replace("*", "").trim()), "Issue Field Label 2 Matched", "Issue Field Label 2 NOT Matched"));
                        if (data.getIssueFieldMandatory2().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("2").contains("*"), true, issueFieldLabel2 + "Label is mandatory and contains '*' ", issueFieldLabel2 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("2");
                    }

                final String issueFieldLabel3 = data.getIssueFieldLabel3();
                if (issueFieldLabel3 != null)
                    if (data.getIssueFieldType3().equalsIgnoreCase("Text Box") && !issueFieldLabel3.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("3"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("3").replaceAll("[^a-zA-Z]+", "").trim(), (issueFieldLabel3.replaceAll("[^a-zA-Z]+", "").trim()), "Issue Field Label 3 Matched", "Issue Field Label 3 NOT Matched"));
                        if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("3").contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("3", "012345");
                    } else if (data.getIssueFieldType3().equalsIgnoreCase("Date") && !issueFieldLabel3.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel3, "Issue Field Label 3 Matched", "Issue Field Label 3 NOT Matched"));
                        if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));

                    } else if (data.getIssueFieldType3().equalsIgnoreCase("Drop Down") && !issueFieldLabel3.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("3"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").replace("*", "").trim(), (issueFieldLabel3.replace("*", "").trim()), "Issue Field Label 3 Matched", "Issue Field Label 3 NOT Matched"));
                        if (data.getIssueFieldMandatory3().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("3").contains("*"), true, issueFieldLabel3 + "Label is mandatory and contains '*' ", issueFieldLabel3 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("3");
                    }

                commonLib.info("Field Type-4 : " + data.getIssueFieldType4());
                final String issueFieldLabel4 = data.getIssueFieldLabel4();
                if (issueFieldLabel4 != null)
                    if (data.getIssueFieldType4().equalsIgnoreCase("Text Box") && !issueFieldLabel4.isEmpty()) {
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("4").replaceAll("[^a-zA-Z]+", "").trim(), (issueFieldLabel4.replaceAll("[^a-zA-Z]+", "")), "Issue Field Label 4 Matched", "Issue Field Label 4 NOT Matched"));
                        if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("4").contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("4", "012345");
                    } else if (data.getIssueFieldType4().equalsIgnoreCase("Date") && !issueFieldLabel4.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), issueFieldLabel4, "Issue Field Label 4 Matched", "Issue Field Label 4 NOT Matched"));
                        if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType4().equalsIgnoreCase("Drop Down") && !issueFieldLabel4.isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("4"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").replace("*", "").trim(), (issueFieldLabel4.replace("*", "").trim()), "Issue Field Label 4 Matched", "Issue Field Label 4 NOT Matched"));
                        if (data.getIssueFieldMandatory4().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("4").contains("*"), true, issueFieldLabel4 + "Label is mandatory and contains '*' ", issueFieldLabel4 + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("4");
                    }

                if (data.getIssueFieldLabel5() != null)
                    if (data.getIssueFieldType5().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel5().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("5"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("5").replaceAll("[^a-zA-Z]+", "").trim(), (data.getIssueFieldLabel5().replaceAll("[^a-zA-Z]+", "").trim()), "Issue Field Label 5 Matched", "Issue Field Label 5 NOT Matched"));
                        if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("5").contains("*"), true, data.getIssueFieldLabel5() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("5", "012345");
                    } else if (data.getIssueFieldType5().equalsIgnoreCase("Date") && !data.getIssueFieldLabel5().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), (data.getIssueFieldLabel5()), "Issue Field Label 5 Matched", "Issue Field Label 5 NOT Matched"));
                        if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, data.getIssueFieldLabel5() + "Label is mandatory and contains '*'", data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType5().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel5().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("5"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").replace("*", "").trim(), (data.getIssueFieldLabel5().replace("*", "").trim()), "Issue Field Label 5 Matched", "Issue Field Label 5 NOT Matched"));
                        if (data.getIssueFieldMandatory5().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("5").contains("*"), true, data.getIssueFieldLabel5() + "Label is mandatory and contains '*'", data.getIssueFieldLabel5() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("5");
                    }

                if (data.getIssueFieldLabel6() != null)
                    if (data.getIssueFieldType6().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel6().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("6"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("6").replaceAll("[^a-zA-Z]+", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()), "Issue Field Label 6 Matched", "Issue Field Label 6 NOT Matched"));
                        if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("6").contains("*"), true, data.getIssueFieldLabel6() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("6", "012345");
                    } else if (data.getIssueFieldType6().equalsIgnoreCase("Date") && !data.getIssueFieldLabel6().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), (data.getIssueFieldLabel6()), "Issue Field Label 6 Matched", "Issue Field Label 6 NOT Matched"));
                        if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, data.getIssueFieldLabel6() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType6().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel6().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("6"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").replace("*", "").trim(), (data.getIssueFieldLabel6().replace("*", "").trim()), "Issue Field Label 6 Matched", "Issue Field Label 6 NOT Matched"));
                        if (data.getIssueFieldMandatory6().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("6").contains("*"), true, data.getIssueFieldLabel6() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel6() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("6");
                    }

                if (data.getIssueFieldLabel7() != null)
                    if (data.getIssueFieldType7().equalsIgnoreCase("Text Box") && !data.getIssueFieldLabel7().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabel("7"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabel("7").replaceAll("[^a-zA-Z]+", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()), "Issue Field Label 7 Matched", "Issue Field Label 7 NOT Matched"));
                        if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabel("7").contains("*"), true, data.getIssueFieldLabel7() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setIssueDetailInput("7", "012345");
                    } else if (data.getIssueFieldType7().equalsIgnoreCase("Date") && !data.getIssueFieldLabel7().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getAvailableDateField());
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getAvailableDateField(), (data.getIssueFieldLabel7()), "Issue Field Label 7 Matched", "Issue Field Label 7 NOT Matched"));
                        if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isDateFieldAvailableMandatory().contains("*"), true, data.getIssueFieldLabel7() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().setDateFieldAvailable(dtf.format(now));
                    } else if (data.getIssueFieldType7().equalsIgnoreCase("Drop Down") && !data.getIssueFieldLabel7().isEmpty()) {
                        commonLib.info(pages.getInteractionsPage().getIssueDetailLabelDropDown("7"));
                        assertCheck.append(actions.assertEqual_stringType(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").replace("*", "").trim(), (data.getIssueFieldLabel7().replace("*", "").trim()), "Issue Field Label 7 Matched", "Issue Field Label 7 NOT Matched"));
                        if (data.getIssueFieldMandatory7().equalsIgnoreCase("Yes")) {
                            assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().getIssueDetailLabelDropDown("7").contains("*"), true, data.getIssueFieldLabel7() + "Label is mandatory and contains '*' ", data.getIssueFieldLabel7() + "Label is mandatory but doesn't contain '*' "));
                        }
                        pages.getInteractionsPage().selectIssueDetailInput("7");
                    }
                pages.getInteractionsPage().sendComment("Automation Suite");
                assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isSaveEnable(), true, "Save Button Enabled Successfully", "Save Button NOT Enabled"));
                pages.getInteractionsPage().clickOnSave();
                assertCheck.append(actions.assertEqual_boolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Ticket Id Visible Successfully over Header", "Ticket Id NOT Visible over Header"));
                commonLib.info(pages.getInteractionsPage().getResolvedFTRDisplayed());
                String[] valueToWrite;
                if (!pages.getInteractionsPage().getResolvedFTRDisplayed().contains("Resolved FTR")) {
                    ticketNumber = pages.getInteractionsPage().getResolvedFTRDisplayed();
                    commonLib.info("Ticket Number:ME " + ticketNumber);
                    valueToWrite = new String[]{ticketNumber};
                    WriteToExcel objExcelFile = new WriteToExcel();
                    commonLib.info("Ticket Number:You " + data.getRowNum());
                    objExcelFile.writeTicketMetaInfo(excelPath, constants.getValue(nftrSheetValue), UtilsMethods.setAllCustomerAttribute(customerNumber, ticketNumber), data.getRowNum());
                    commonLib.pass("Ticket Number Written to Excel " + valueToWrite[0]);
                } else {
                    commonLib.fail("It's FTR not NFTR", true);
                }
                pages.getInteractionsPage().closeInteractions();
                SMSHistoryPOJO smsHistory = api.smsHistoryTest(customerNumber);
                SMSHistoryList list = smsHistory.getResult().get(0);
                commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
                try {
                    assertCheck.append(actions.assertEqual_boolean(list.getMessageText().contains(ticketNumber), true, "Message Sent for same ticket id which has been Created", "Message does not send for same ticket id which has been Created"));
                    assertCheck.append(actions.assertEqual_stringType(list.getSmsType().toLowerCase().trim(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE).toLowerCase().trim(), "Message type is system", "Message type is not system"));
                    assertCheck.append(actions.assertEqual_boolean(list.isAction(), false, "Action button is disabled", "Action button is NOT disabled"));
                    assertCheck.append(actions.assertEqual_stringType(list.getTemplateName().toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
                } catch (NullPointerException e) {
                    commonLib.fail("Not able to validate Message sent to customer or not. " + e.getMessage(), true);
                }

            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Exception in Method - CreateNFTRInteraction" + e.fillInStackTrace(), true);
                pages.getInteractionsPage().closeInteractions();
                pages.getInteractionsPage().clickOnContinueButton();
            }
        }catch (Exception e){
            commonLib.fail("Exception in Method - CreateNFTRInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
