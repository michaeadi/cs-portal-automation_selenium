package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.DetailAccountInfoWidget;
import com.airtel.cs.model.cs.response.accountinfo.AccountDetails;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccountInfoBillDisputeTest extends Driver {

  private static String customerNumber = null;
  String comments = "Adding comment using Automation Raise Dispute";
  RequestSource api = new RequestSource();
  String accountNumber = "";

  @BeforeMethod(groups = {"SanityTest", "RegressionTest"})
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }


  /**
   * This method is used to Open Customer Profile Page with valid MSISDN
   */
  @Test(priority = 1, groups = {"SanityTest", "RegressionTest"})
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
   * This method is used to check whether user has permission for Account Information Widget
   */
  @Test(priority = 2, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
  public void isUserHasAccountInformationPermission() {
    try {
      selUtils.addTestcaseDescription("Verify that account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view account information Widget Permission", "description");
      boolean accountInfoPermission = UtilsMethods
          .isUserHasPermission(constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION));
      String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
      if (connectionType.equalsIgnoreCase("POSTPAID")) {
        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInfoWidgetDisplayWithOutScroll(), accountInfoPermission, "Account Information Widget displayed correctly as per user permission", "Account Information Widget does not display correctly as per user permission"));
        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isActionIconVisibleOnAccountInfo(), accountInfoPermission, "Account Information Detail Icon displayed correctly as per user permission", "Account Information Detail Icon does not display correctly as per user permission"));
        accountNumber = pages.getAccountInformationWidget().getAccountNumber();
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - isUserHasAccountInformationPermission" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }

  /**
   * This method is used to check whether user has permission for Account Information Widget
   */
  @Test(priority = 3, groups = { "SanityTest", "RegressionTest" }, dependsOnMethods = { "openCustomerInteraction" })
  public void verifyAccountInfoDetailsPage() {
    try {
      selUtils.addTestcaseDescription("Verify that detailed account info icon should be visible to the logged in agent", "description");
      final DetailAccountInfoWidget acctountDetailsWidget = pages.getDetailAccountInfoWidget();
      acctountDetailsWidget.openAccountInformationDetailPage();
      assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
      final boolean umViewBillPermission = UtilsMethods
          .isUserHasPermission(constants.getValue(PermissionConstants.BILL_DISPUTE));
      AccountDetails accountDetails = api.getAccountInfoDetail(accountNumber, 1);
      int size = accountDetails.getTotalCount() > 5 ? 5 : accountDetails.getTotalCount();
      int totalCount = accountDetails.getTotalCount();
      int pageNumber=1;
      String ticketId="";
      for (int row = 1; row <= size; row++) {
        String transactionType =acctountDetailsWidget.getTransactionType(row);
        if ("INVOICE".equalsIgnoreCase(transactionType) && umViewBillPermission) {
          assertCheck.append(actions.assertEqualBoolean(acctountDetailsWidget.isBillDisputeDisplay(row), umViewBillPermission , "Bill dispute button visible", "Bill dispute button is not visible"));
          String billNumber = acctountDetailsWidget.getBillNumber(row).split(":")[1].trim();
          String billStatus = acctountDetailsWidget.getBillStatus(row);
          acctountDetailsWidget.openBillDisputePage(row);
          pages.getCustomerProfilePage().clickCancelBtnWithOutContinue();
          acctountDetailsWidget.openBillDisputePage(row);

          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeDetailsHeader().toUpperCase(), "RAISE DISPUTE", "Bill Dispute page display as expected", "Bill Dispute page doesnot not display as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeNumber(), billNumber, "Bill Number visible same as account info details", "Bill Number not visible as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeAccountNumber(), accountNumber, "Account Number visible same as account info details", "Account Number not visible  as expected"));
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getBillDisputeStatus(), billStatus, "Bill Status visible same as account info details", "Bill Status not visible as expected as expected"));

          pages.getAuthTabPage().clickSelectReasonDropDown();
          reason = pages.getAuthTabPage().getReason();
          pages.getAuthTabPage().chooseReason();
          pages.getAuthTabPage().enterComment(comments);
          pages.getAuthTabPage().clickSubmitBtn();
          final String toastText = acctountDetailsWidget.getToastText();
          assertCheck.append(actions.assertEqualBoolean(toastText.contains("SR has been successfully raised"), true, "SR has been created successfully as expected", "SR has not been created successfully as expected"));
          ticketId = toastText.substring(toastText.indexOf("ID") + 6, toastText.indexOf("and") - 1);
          assertCheck.append(actions.assertEqualStringType(acctountDetailsWidget.getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
          pages.getCustomerProfilePage().goToViewHistory();
          pages.getViewHistory().clickOnInteractionsTab();
          String comment = pages.getViewHistory().getLastCreatedComment();
          pages.getViewHistory().goToTicketHistoryTab();
          String ticketIdFromTab = pages.getViewHistory().getLastTicketId();
          assertCheck.append(actions.assertEqualStringType(comment.toLowerCase().trim(), comments.toLowerCase(), "Comments found in view history Interaction tab", "Comment doesn't found in view history interaction tab"));
          assertCheck.append(actions.assertEqualStringType(ticketIdFromTab, ticketId, "Ticket Id found in view history Ticket tab", "Ticket Id doesn't found in view history ticket tab"));

          break;
        }
        if (row == 5 && pageNumber * 5 != totalCount && acctountDetailsWidget.nextPageVisible()) {
          acctountDetailsWidget.clickNextPage();
          accountDetails = api.getAccountInfoDetail(accountNumber, ++pageNumber);
          row = 0;
        } else if (pageNumber * 5 == totalCount) {
          break;
        }
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - verifyAccountInfoDetailsPage" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }
}
