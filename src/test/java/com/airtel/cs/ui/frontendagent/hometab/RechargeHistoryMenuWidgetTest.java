package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.rechargehistory.RechargeHistory;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RechargeHistoryMenuWidgetTest extends Driver {
    public static final String RUN_RECHARGE_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_RECHARGE_WIDGET_TESTCASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    private RechargeHistory rechargeHistoryAPI;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkRechargeHistoryFlag() {
        if (!StringUtils.equals(RUN_RECHARGE_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Recharge widget Test Case Flag Value is - " + RUN_RECHARGE_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.RECHARGE_HISTORY_MSISDN);
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

    @DataProviders.Table(name = "More Recharge History")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void rechargeHistoryHeaderTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Recharge History's  Menu of User :" + customerNumber + ",validate recharge menu widget display all header display as per config", "description");
            rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            widgetMethods.clickMenuButton(pages.getRechargeHistoryWidget().getUniqueIdentifier());
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getMoreRechargeHistoryPage().getUniqueIdentifier()), true, "Recharge History's MENU is visible ", "Recharge History's MENU is not visible ", true));
            if (pages.getRechargeHistoryWidget().isResultPresent(rechargeHistoryAPI)) {
                selUtils.addTestcaseDescription("Validating Recharge History's  Menu of User :" + customerNumber + "validate recharge menu widget display all header display as per config", "description");

                for (int i = 0; i < data.getHeaderName().size(); i++) {
                    String headerName = i == 3 ? pages.getMoreRechargeHistoryPage().getHeaders(i + 1) + pages.getMoreRechargeHistoryPage().getSubHeaders(4).replace("|", "") : pages.getMoreRechargeHistoryPage().getHeaders(i + 1);
                    assertCheck.append(actions.matchUiAndAPIResponse(headerName, data.getHeaderName().get(i).replace("|", ""), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                }

            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryHeaderTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "More Recharge History")
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction", "rechargeHistoryHeaderTest"})
    public void rechargeHistoryMenuTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Recharge History's  Menu of User :" + customerNumber + "validate recharge menu widget display all header display as per config,Validate all the data rows must be display as per api response.", "description");
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getMoreRechargeHistoryPage().getUniqueIdentifier()), true, "Recharge History's MENU is visible ", "Recharge History's MENU is not visible ", true));
            assertCheck.append(actions.assertEqualBoolean(pages.getMoreRechargeHistoryPage().isDatePickerVisible(), true, "Date Picker is visible as expected", "Date picker is not visible "));
            rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            if (pages.getRechargeHistoryWidget().isResultPresent(rechargeHistoryAPI)) {
                int size = pages.getMoreRechargeHistoryPage().getNumbersOfRows();
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 1).trim(), rechargeHistoryAPI.getResult().get(i).getCharges().trim(), " Charges received is as expected on row " + i, " Charges received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), constants.getValue(CommonConstants.UI_RECHARGE_HISTORY_PATTERN), constants.getValue(CommonConstants.API_RECHARGE_HISTORY_PATTERN)), "Date & Time received is as expected on row " + i, "Date & Time received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 3).replaceAll("[^a-zA-Z]+", ""), rechargeHistoryAPI.getResult().get(i).getBundleName().replaceAll("[^a-zA-Z]+", ""), "Bundle Name received is as expected on row " + i, "Bundle Name received is not as expected on row " + i));
                    String apiValue = ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA()) + ((rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS() == null) ? "" : rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS());
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 4).replaceAll("[^a-zA-Z0-9]+", ""), apiValue.replaceAll("[^a-zA-Z0-9]+", ""), "Recharge Benefits received is as expected on row " + i, "Recharge Benefits received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 5), rechargeHistoryAPI.getResult().get(i).getStatus().trim().toLowerCase(), "Status received is as expected on row " + i, "Status received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 6), rechargeHistoryAPI.getResult().get(i).getMode(), "Mode of recharge received is as expected on row " + i, "Mode of recharge received is not as expected on row " + i));
                    if (rechargeHistoryAPI.getResult().get(i).getMode().equalsIgnoreCase("Voucher")) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 7).trim(), rechargeHistoryAPI.getResult().get(i).getSerialNumber().trim(), "Serial Number received is as expected on row " + i, "Serial Number received is not as expected on row " + i));
                    } else {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 7), "-", "Serial Number received is as expected on row " + i, "Serial Number received is not as expected on row " + i));
                    }
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 8), ((rechargeHistoryAPI.getResult().get(i).getExpiryDate() == null) ? "-" : UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getExpiryDate(), constants.getValue(CommonConstants.API_RECHARGE_HISTORY_PATTERN), constants.getValue(CommonConstants.API_RECHARGE_HISTORY_PATTERN))), "Expiry date received is as expected on row " + i, "Expiry date received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 9), ((rechargeHistoryAPI.getResult().get(i).getOldExpiryDate() == null) ? "-" : UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getOldExpiryDate(), constants.getValue(CommonConstants.API_RECHARGE_HISTORY_PATTERN), constants.getValue(CommonConstants.API_RECHARGE_HISTORY_PATTERN))), "Old Expiry date received is as expected on row " + i, "Old Expiry date received is not as expected on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 10).trim(), ((rechargeHistoryAPI.getResult().get(i).getValidity() == null) ? "-" : rechargeHistoryAPI.getResult().get(i).getValidity()), "Validity received is as expected on row " + i, "Validity received is not as expected on row " + i));
                    if (i != 0) {
                        assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isSortOrderDisplay(pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i, 2), pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2), constants.getValue(CommonConstants.UI_RECHARGE_HISTORY_PATTERN)), true, "In Sort order display on ui as expected", pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i + 1, 2) + "should not display before " + pages.getMoreRechargeHistoryPage().getValueCorrespondingToRechargeHeader(i, 2)));
                    }
                }
            }
            pages.getMoreRechargeHistoryPage().goingBackToHomeTab();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryMenuTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
