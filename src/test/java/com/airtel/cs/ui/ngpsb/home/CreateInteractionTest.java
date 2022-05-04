package com.airtel.cs.ui.ngpsb.home;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.excelutils.WriteToExcel;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import com.airtel.cs.model.cs.response.smshistory.SMSHistory;
import com.airtel.cs.model.cs.response.smshistory.SMSHistoryList;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
    @Log4j2
    public class CreateInteractionTest extends Driver {

        private static String customerNumber = null;
        PsbRequestSource psbApi = new PsbRequestSource();
        CLMDetailsResponse clmDetails;
        String space = " ";
        String className = this.getClass().getName();
        RequestSource api = new RequestSource();

        @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
        public void checkExecution() {
            if (!continueExecutionFA) {
                commonLib.skip("Skipping tests because user NOT able to login Over Portal");
                throw new SkipException("Skipping tests because user NOT able to login Over Portal");
            }
        }

        @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
        public void openCustomerInteraction() {
            try {
                selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
                customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
                pages.getSideMenuPage().clickOnSideMenu();
                pages.getSideMenuPage().openCustomerInteractionPage();
                pages.getMsisdnSearchPage().enterNumber(customerNumber);
                pages.getMsisdnSearchPage().clickOnSearch();
                clmDetails = psbApi.getCLMDetails(customerNumber);
                assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
                if (clmDetails.getStatusCode() == 200) {
                    boolean pageLoaded = pages.getPsbDemographicWidget().isPageLoaded(clmDetails, className);
                    if (!pageLoaded)
                        continueExecutionFA = false;
                } else
                    commonLib.warning("Clm Details API is not working");
                actions.assertAllFoundFailedAssert(assertCheck);
            } catch (Exception e) {
                commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
            }
        }

        @Test(priority = 2, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "getTestData1", dataProviderClass = DataProviders.class)
        public void createInteraction(FtrDataBeans data) {
            try {
                final String issueCode = data.getIssueCode();
                selUtils.addTestcaseDescription(" Validating FTR Ticket: " + issueCode, "description");
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
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is as expected", "Issue is not as expected "));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is as expected", "Issue sub sub type is not as expected "));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is as expected", "Issue type is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is as expected", "Issue sub type is not as expected "));
                pages.getInteractionsPage().sendComment(constants.getValue(ApplicationConstants.COMMENT));
                pages.getInteractionsPage().clickOnSave();
                assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isResolvedFtrVisible(), true, "Resolved FTR displayed", "Resolved FTR not displayed"));
                SMSHistory smsHistory = api.smsHistoryTest(customerNumber);
                SMSHistoryList list = smsHistory.getResult().get(0);
                commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
                if (StringUtils.equalsIgnoreCase(data.getMessageConfigured(), "true")) {
                    assertCheck.append(actions.assertEqualBoolean(list.getMessageText().contains(issueCode), true, "Message sent to customer for same FTR Category for which Issue has been Created", "Message does not sent to customer for same FTR Category for which Issue has been Created"));
                    assertCheck.append(actions.assertEqualStringType(list.getSmsType(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE), "Message type is system", "Message type is not system"));
                    assertCheck.append(actions.assertEqualBoolean(list.getAction(), false, "Action button is disabled", "Action button is not disabled"));
                    assertCheck.append(actions.assertEqualStringType(list.getTemplateName().toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
                }
                pages.getInteractionsPage().closeInteractions();
            } catch (NoSuchElementException | TimeoutException | ElementClickInterceptedException e) {
                commonLib.fail("Exception in Method - createInteraction" + e.fillInStackTrace(), true);
                pages.getInteractionsPage().clickOutside();
                pages.getInteractionsPage().resetInteractionIssue();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        }

        @Test(priority = 3, dependsOnMethods = "openCustomerInteraction", groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "getTestData2", dataProviderClass = DataProviders.class)
        public void CreateNFTRInteraction(NftrDataBeans data) {
            String ticketNumber = null;
            try {
                final String issueCode = data.getIssueCode();
                selUtils.addTestcaseDescription(" Validating NFTR Ticket: " + issueCode, "description");
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
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssue().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue is as expected", "Issue is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub sub type is as expected", "Issue sub sub type is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue type is as expected", "Issue type is not as expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getInteractionsPage().getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), data.getIssueSubType().replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), "Issue sub type is as expected", "Issue sub type is not as expected"));
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel1(), data.getIssueFieldType1(), data.getIssueFieldMandatory1(), "1");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel2(), data.getIssueFieldType2(), data.getIssueFieldMandatory2(), "2");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel3(), data.getIssueFieldType3(), data.getIssueFieldMandatory3(), "3");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel4(), data.getIssueFieldType4(), data.getIssueFieldMandatory4(), "4");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel5(), data.getIssueFieldType5(), data.getIssueFieldMandatory5(), "5");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel6(), data.getIssueFieldType6(), data.getIssueFieldMandatory6(), "6");
                pages.getInteractionsPage().fillIssueFields(data.getIssueFieldLabel7(), data.getIssueFieldType7(), data.getIssueFieldMandatory7(), "7");
                pages.getInteractionsPage().sendComment(constants.getValue(ApplicationConstants.COMMENT));
                assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isSaveEnable(), true, "Save Button Enabled Successfully", "Save Button NOT Enabled"));
                pages.getInteractionsPage().clickOnSave();
                assertCheck.append(actions.assertEqualBoolean(pages.getInteractionsPage().isTicketIdVisible(), true, "Ticket Id Visible Successfully over Header", "Ticket Id NOT Visible over Header"));
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
                SMSHistory smsHistory = api.smsHistoryTest(customerNumber);
                SMSHistoryList list = smsHistory.getResult().get(0);
                commonLib.info("Message Sent after Ticket Creation: " + list.getMessageText());
                assert ticketNumber != null;
                assertCheck.append(actions.assertEqualBoolean(list.getMessageText().contains(ticketNumber), true, "Message Sent for same ticket id which has been Created", "Message does not send for same ticket id which has been Created"));
                assertCheck.append(actions.assertEqualStringType(list.getSmsType().toLowerCase().trim(), constants.getValue(CommonConstants.SYSTEM_SMS_TYPE).toLowerCase().trim(), "Message type is system", "Message type is not system"));
                assertCheck.append(actions.assertEqualBoolean(list.getAction(), false, "Action button is disabled", "Action button is NOT disabled"));
                assertCheck.append(actions.assertEqualStringType(list.getTemplateName().toLowerCase().trim(), constants.getValue(CommonConstants.TICKET_CREATED_EVENT).toLowerCase().trim(), "Template event is same as defined", "Template event not same as defined"));
            } catch (Exception e) {
                commonLib.fail("Exception in Method - CreateNFTRInteraction" + e.fillInStackTrace(), true);
                pages.getInteractionsPage().closeInteractions();
                pages.getInteractionsPage().clickOnContinueButton();
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        }
}
