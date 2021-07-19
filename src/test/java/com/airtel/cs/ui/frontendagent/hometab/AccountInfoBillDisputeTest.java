package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AMTransactionsWidget;
import com.airtel.cs.pagerepository.pagemethods.DetailAccountInfoWidget;
import com.airtel.cs.pojo.response.accountinfo.AccountDetails;
import io.restassured.http.Headers;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.IntStream;

public class AccountInfoBillDisputeTest extends Driver {

  private static String customerNumber = null;
  String comments = "Adding comment using Automation";
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
      customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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
   * This method is used to validate MSISDN
   */

  @Test(priority = 2, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
  public void invalidMSISDNTest() {
    try {
      selUtils.addTestcaseDescription("Validating the Demographic Information of User with invalid MSISDN : 123456789", "description");
      pages.getDemoGraphicPage().enterMSISDN("123456789");
      assertCheck.append(actions
          .assertEqualStringType(pages.getDemoGraphicPage().invalidMSISDNError(), "Entered customer number is Invalid",
              "Error Message Correctly Displayed", "Error Message NOT Displayed Correctly"));
      actions.assertAllFoundFailedAssert(assertCheck);
    } catch (Exception e) {
      commonLib.fail("Exception in Method - invalidMSISDNTest" + e.fillInStackTrace(), true);
    }
  }

  /**
   * This method is used to check whether user has permission for Account Information Widget
   */
  @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
  public void isUserHasAccountInformationPermission() {
    try {
      selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
      Boolean accountInfoPermission = UtilsMethods
          .isUserHasPermission(new Headers(map), constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION));
      String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
      if (connectionType.equalsIgnoreCase("POSTPAID")) {
        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), accountInfoPermission, "Account Information Widget displayed correctly as per user permission", "Account Information Widget does not display correctly as per user permission"));
        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isActionIconVisibleOnAccountInfo(), accountInfoPermission, "Account Information Detail Icon displayed correctly as per user permission", "Account Information Detail Icon does not display correctly as per user permission"));
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - isUserHasAccountInformationPermission" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }

  /**
   * This method is used to check whether user has permission for Account Information Widget
   */
  @Test(priority = 4, groups = { "SanityTest", "RegressionTest", "ProdTest" }, dependsOnMethods = { "openCustomerInteraction",
      "isUserHasAccountInformationPermission" })
  public void verifyAccountInfoDetailsPage() {
    try {
      selUtils.addTestcaseDescription("Verify that detailed account info icon should be visible to the logged in agent", "description");
      final DetailAccountInfoWidget acctountDetailsWidget = pages.getDetailAccountInfoWidget();
      acctountDetailsWidget.openAccountInformationDetailPage();
      assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
      final Boolean umViewBillPermission = UtilsMethods
          .isUserHasPermission(new Headers(map), constants.getValue(PermissionConstants.VIEW_POSTPAID_BILL));
      AccountDetails accountDetails = api.getAccountInfoDetail(pages.getAccountInformationWidget().getAccountNumber());
      accountDetails.getTotalCount();
      for (int i = 0; i < accountDetails.getTotalCount(); i++) {
        String transactionType =acctountDetailsWidget.getTransactionType(i+1);
        if("INVOICE".equalsIgnoreCase(transactionType) && umViewBillPermission){
          assertCheck.append(actions.assertEqualBoolean(acctountDetailsWidget.isBillDisputeDisplay(i + 1), umViewBillPermission , "Bill dispute button visible", "Bill dispute button is not visible"));
          String billNumber = acctountDetailsWidget.getBillNumber(i + 1);
          String accountNumber = pages.getAccountInformationWidget().getAccountNumber();
          String billDateTime = acctountDetailsWidget.getBillDateTime(i + 1);
          String billStatus = acctountDetailsWidget.getBillStatus(i + 1);

          acctountDetailsWidget.openBillDisputePage(i + 1);
          pages.getCustomerProfilePage().clickCloseBtn();
          acctountDetailsWidget.openBillDisputePage(i + 1);

          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeDetailsHeader().toUpperCase(), "Bill Dispute", "Bill Dispute page display as expected", "Bill Dispute page doesnot not display as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeNumber(), billNumber, "Bill Number visible same as account info details", "Bill Number not visible as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeAccountNumber(), accountNumber, "Account Number visible same as account info details", "Account Number not visible  as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeDateTime(), billDateTime, "Bill Date and Time visible same as account info details", "Bill Date and Time not visible as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeStatus(), billStatus, "Bill Status visible same as account info details", "Bill Status not visible as expected as expected"));

          pages.getAuthTabPage().clickSelectReasonDropDown();
          reason = pages.getAuthTabPage().getReason();
          pages.getAuthTabPage().chooseReason();
          pages.getAuthTabPage().enterComment(comments);
          pages.getAuthTabPage().clickSubmitBtn();
          final String toastText = pages.getAuthTabPage().getToastText();
          assertCheck.append(actions.assertEqualStringType(toastText, "Internet Settings has been sent on Customer`s Device.", "Send Internet Settings Message has been sent to customer successfully", "Send Internet Settings Message hasn't been sent to customer ans message is :-" + toastText));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));

          break;
        }
      }
      pages.getCustomerProfilePage().goToViewHistory();
      pages.getViewHistory().goToTicketHistoryTab();
      String issueCode = pages.getViewHistory().getLastCreatedIssueCode();
      pages.getViewHistory().clickOnInteractionsTab();
      issueCode = pages.getViewHistory().getLastCreatedIssueCode();
//      assertCheck.append(actions.assertEqualStringType(issueCode.toLowerCase().trim(), data.getIssueCode().trim().toLowerCase(), "Issue code found in view history", "Issue code doesn't found in view history"));

    } catch (Exception e) {
      commonLib.fail("Exception in Method - verifyAccountInfoDetailsPage" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }
}
