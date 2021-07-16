package com.airtel.cs.ui.frontendagent.hometab.widgetvisibility;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.agents.Authorities;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class VisibiltyByCustomerSegment extends Driver {

  private static String customerNumber = null;
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
      customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
      pages.getSideMenuPage().clickOnSideMenu();
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

  @Test(priority = 2, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void connectionTypeValidity() {
    String connectType = pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim();
    if(StringUtils.isEmpty(connectType) || "none".equals(connectType)){
      connectType =constants.getValue("defaultConnectionType").toLowerCase().trim();
    }
    String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_PERMISSION);
    String vasDetails_permission = constants.getValue(PermissionConstants.VAS_DETAILS_WIDGET_PERMISSION);
    String accountInfo_permission = constants.getValue(PermissionConstants.ACCOUNT_INFORMATION_WIDGET_PERMISSION);
    String currentPlan_permission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
    String amTxnHistory_permission = constants.getValue(PermissionConstants.AM_TXNHISTORY_WIDGET_PERMISSION);
    String loanService_permission = constants.getValue(PermissionConstants.LOAN_SERVICE_WIDGET_PERMISSION);
    String ringTone_permission = constants.getValue(PermissionConstants.RINGTONE_WIDGET_PERMISSION);
    String planAndPackPermission = constants.getValue(PermissionConstants.CURRENT_PLAN_WIDGET_PERMISSION);
    try {
      selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate All Widgets are according to connection type ", "description");
      List<Authorities> allPermissions =UtilsMethods.getAgentDetail(new Headers(map)).getUserDetails().getUserDetails().getAuthorities();
      assertCheck.append(actions.assertEqualStringNotNull(connectType, "Customer Connection Type is not empty", "Customer Connection Type is empty"));
      if ("prepaid".equals(connectType) || "all".equals(connectType) || "hybrid".equals(connectType)) {
        assertCheck.append(actions.assertEqualBoolean(pages.getRechargeHistoryWidget().isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetVisible(), true, "Current Balance Widget is visible", "Current Balance Widget is not visible"));
      }
      if ("postpaid".equals(connectType) || "all".equals(connectType)) {
        assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), checkPermission(allPermissions,accountInfo_permission), "Account Information Widget displayed correctly as per user permission", "Account Information Widget does not display correctly as per user permission"));
        assertCheck.append(actions.assertEqualBoolean(pages.getCurrentPlanWidget().isCurrentPlanWidgetDisplay(), checkPermission(allPermissions,currentPlan_permission), "Current plan Widget displayed correctly as per user permission", "Current plan Widget does not display correctly as per user permission"));
        pages.getPlanAndPackDetailedWidget().openCurrentPlanDetailPage();
        assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPlanWidgetDisplay(), checkPermission(allPermissions, planAndPackPermission), "Plan Widget displayed correctly as per user permission", "Plan Widget does not display correctly as per user permission"));
        assertCheck.append(actions.assertEqualBoolean(pages.getPlanAndPackDetailedWidget().isPackWidgetDisplay(), checkPermission(allPermissions, planAndPackPermission), "Pack Widget displayed correctly as per user permission", "Pack Widget does not display correctly as per user permission"));
        pages.getCustomerProfilePage().goToHomeTab();
      }
      if(!"none".equals(connectType)){
        assertCheck.append(actions.assertEqualBoolean(pages.getUsageHistoryWidget().isUsageHistoryWidgetIsVisible(), true, "Usage History Widget is visible", "Usage History Widget is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getAmTxnWidgetPage().isAirtelMoneyTransactionWidgetVisible(), checkPermission(allPermissions, amTxnHistory_permission), "Airtel Money Transaction Widget is visible", "Airtel Money Transaction Widget is not visible"));
        assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isServiceClassWidgetDisplay(), checkPermission(allPermissions, hlr_permission), "Service Profile Widget displayed correctly as per user permission", "Service Profile Widget does not display correctly as per user  "));
        assertCheck.append(actions.assertEqualBoolean(pages.getLoanWidget().isLoanServiceWidgetVisible(), checkPermission(allPermissions, loanService_permission), "Loan Service Widget displayed correctly as per user permission", "Loan Service Widget does not display correctly as per user "));
        assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidgetPage().isActiveVasWidgetVisible(), checkPermission(allPermissions, vasDetails_permission), "Active Vas Widget displayed correctly as per user permission", "Active Vas Widget does not display correctly as per user"));
        assertCheck.append(actions.assertEqualBoolean(pages.getCrbtWidgetPage().isCRBTWidgetDisplay(), checkPermission(allPermissions, ringTone_permission), "CRBT Widget displayed correctly as per user permission", "CRBT Widget does not display correctly as per user"));
      }
      actions.assertAllFoundFailedAssert(assertCheck);

    } catch (Exception e) {
      commonLib.fail("Exception in Method - connectionTypeValidity" + e.fillInStackTrace(), true);
    }
  }

  /**
   * This method use to get check permission from agent permissions
   * @param allPermissions
   * @param permission
   * @return Boolean
   * */
  private Boolean checkPermission(List<Authorities> allPermissions, String permission) {
    return allPermissions.stream().anyMatch(authorities -> authorities.getAuthority().equalsIgnoreCase(permission));
  }
}