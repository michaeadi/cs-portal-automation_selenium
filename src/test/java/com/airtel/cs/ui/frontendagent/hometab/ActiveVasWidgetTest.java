package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ActiveVasWidgetTest extends Driver {

    public static final String RUN_VAS_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_VAS_WIDGET_TESTCASE);
    private static String customerNumber=null;
    private List<String> vasSubscriptionHistory;


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkActiveVasWidgetFlag() {
        if (!StringUtils.equals(RUN_VAS_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run VAS widget Test Case Flag Value is - " + RUN_VAS_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality is not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.ACTIVE_VAS_MSISDN);
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testHeaderAndAuuid() {
        try {
            selUtils.addTestcaseDescription("Validate Active VAS Widget visibility ,Validate Footer and Middle Auuid", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isActiveVasWidgetVisible(), true, "Active Vas Widget is visible", "Active Vas Widget is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isActiveVasWidgetHeaderVisible(), true, "Active Vas header is visible", "Active Vas header is not visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the Active VAS widget and is expected ", "Auuid NOT shown at the footer of Active VAS widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the Active VAS widget and is expected", "Auuid NOT shown at the middle of Active VAS widget"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testHeaderAndAuuid" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testActiveVasWidget() {
        try {
            selUtils.addTestcaseDescription("Validate all the column names in Active VAS widget", "description");
            vasSubscriptionHistory = api.getVasSubscriptionHistory(customerNumber);
            if (Integer.parseInt(pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "statusCode", "statusCode"))==200 && pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "status", "status").equalsIgnoreCase("FAILED")) {
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            }
            else if (Integer.parseInt(pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "statusCode", "statusCode"))==500 && pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "statusCode", "statusCode").equalsIgnoreCase("FAILED")) {
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Active Vas Details", true);
            }else {
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isServiceNameLabelVisible(), true, "Service Name is visible", "Service Name is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isStartDateLabelVisible(), true, "Start Date is visible", "Start Date is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isRenewalDateLabelVisible(), true, "Renewal Date is visible", "Renewal Date is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isLastChargedLabelVisible(), true, "Last Charged is visible", "Last Charged is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isVendorLabelVisible(), true, "Vendor is visible", "Vendor is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isActionVisible(), true, "Action is visible", "Action is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isMoreIcon(), true, "Detailed Page icon is visible", "Detailed Page icon is NOT visible"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testActiveVasWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction","testActiveVasWidget"})
    public void testActiveVasData() {
        try {
            selUtils.addTestcaseDescription("Validate all the data in column names in Active VAS widget", "description");
            Integer size = pages.getActiveVasWidget().getNumberOfRows();
            String ServiceName = pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "vasName", "vasName");
            String VasStartDate = UtilsMethods.getDateFromEpoch(Long.parseLong(pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "vasStartDate", "vasStartDate")), constants.getValue(CommonConstants.ACTIVE_VAS_START_DATE_PATTERN));
            String NextRenewDate = UtilsMethods.getDateFromEpoch(Long.parseLong(pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "nextRenewDate", "nextRenewDate")), constants.getValue(CommonConstants.ACTIVE_VAS_RENEWAL_DATE_PATTERN));
            String RenewalValidity = pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "validityWithUnit", "validityWithUnit");
            String LastChargedAmount = pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "subscriptionAmountWithCurrency", "subscriptionAmountWithCurrency");
            String LastChargedDate = UtilsMethods.getDateFromEpoch(Long.parseLong(pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "lastChargedDate", "lastChargedDate").replace("}", "")), constants.getValue(CommonConstants.ACTIVE_VAS_LAST_RECHARGED_DATE_PATTERN));
            String Vendor = pages.getAccountInformationWidget().getValue(vasSubscriptionHistory, "vendor", "vendor");
            for (int i = 0; i < size; i++) {
                int row = i + 1;
                if (vasSubscriptionHistory.size()==2) {
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 1).toLowerCase(), ServiceName, "Service Name is expected at row number :" + row, "Service Name is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 2).toLowerCase(), VasStartDate, "Start Date is expected at row number :" + row, "Start Date is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 3), NextRenewDate, "Renewal Date is expected at row number :" + row, "Renewal Date is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderSubValue(row, 3).toLowerCase(), RenewalValidity, "Renewal validity is expected at row number :" + row, "Renewal validity is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 4), LastChargedAmount.toLowerCase(), " Last Charged Amount is expected at row number :" + row, "Last Charged Amount is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderSubValue(row, 4), LastChargedDate, "Last Charged Date is expected at row number :" + row, "Last Charged Date is Not as expected at row number :" + row));
                    assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 5).toLowerCase(), Vendor, "Vendor is expected at row number :" + row, "Vendor is Not as expected at row number :" + row));
                } else {
                    for (int j = 1; j < vasSubscriptionHistory.size(); j++) {
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 1), ServiceName, "Service Name is expected at row number :" + row, "Service Name is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 2), VasStartDate, "Start Date is expected at row number :" + row, "Start Date is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 3), NextRenewDate, "Renewal Date is expected at row number :" + row, "Renewal Date is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderSubValue(row, 3), RenewalValidity, "Renewal validity is expected at row number :" + row, "Renewal validity is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 4), LastChargedAmount, " Last Charged Amount is expected at row number :" + row, "Last Charged Amount is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderSubValue(row, 4), LastChargedDate, "Last Charged Date is expected at row number :" + row, "Last Charged Date is Not as expected at row number :" + row));
                        assertCheck.append(actions.assertEqualStringType(pages.getActiveVasWidget().getHeaderValue(row, 5), Vendor, "Vendor is expected at row number :" + row, "Vendor is Not as expected at row number :" + row));
                    }
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testActiveVasData" + e.fillInStackTrace(), true);
        }
    }

        @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testDetailedWidget() {
        try {
            selUtils.addTestcaseDescription("Validate detailed Page of Active VAS Widget ", "description");
            pages.getActiveVasWidget().clickMoreIcon();
            assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isActiveVasDetailedWidgetVisible(), true, "Active Vas Detailed Widget is visible", "Active Vas Detailed Widget is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isSubscriptionVasWidgetVisible(), true, "Subscription Widget is visible", "Subscription Widget is not visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getActiveVasWidget().isAvailableVasWidgetVisible(), true, "Available Vas Widget is visible", "Available Vas Widget is not visible"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testDetailedWidget" + e.fillInStackTrace(), true);
        }
    }




}
