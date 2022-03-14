package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.usagehistory.UsageHistory;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UsageHistoryDetailedWidgetTest extends Driver {
    public static final String RUN_USAGE_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_USAGE_WIDGET_TESTCASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    private UsageHistory usageHistoryAPI;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkUsageHistoryFlag() {
        if (!StringUtils.equals(RUN_USAGE_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Usage widget Test Case Flag Value is - " + RUN_USAGE_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.USAGE_HISTORY_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Detailed Usage History")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void usageHistoryMenuHeaderTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validate Usage history menu widget header visible and display all the Column name as per config,validate all the filter displayed,Validate all type of cdr displayed.,Validation Pagination display when data displayed on UI.", "description");
            pages.getUsageHistoryWidget().openingMoreDetails();
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), true, "Detailed Usage History Widget visible ", "Detailed Usage History Widget does not visible ", true));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isFreeCDR(), true, "Free CDR Option does display on UI.", "Free CDR Option does not display on UI."));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isTypeOfCDR(), true, "Type of CDR Option does display on UI.", "Type of CDR Option does not display on UI."));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isTodayDateFilter(), true, "Today date filter Option does display on UI.", "Today date filter Option does not display on UI."));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isLast2DayDateFilter(), true, "Last 2 Days date filter Option does display on UI.", "Last 2 Days date filter Option does not display on UI."));
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isLast7DayDateFilter(), true, "Last 7 Days date filter Option does display on UI.", "Last 7 Days date filter Option does not display on UI."));
            usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
            final int statusCode = usageHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Usage History Detailed Widget API success and status code is :" + statusCode, "Usage History Detailed Widget API got failed and status code is :" + statusCode, false));
            if (statusCode != 200) {
                commonLib.fail(constants.getValue("cs.get.usage.history.api.error"), false);
                if(statusCode == 500){
                    assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isUnableToFetch(),true, constants.getValue("usage.history.error.widget.visible"),constants.getValue("usage.history.error.widget.not.visible"),true));
                }
            } else if (usageHistoryAPI.getResult().size() > 0) {

                for (int i = 0; i < data.getHeaderName().size(); i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                }

            } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("No Usage History Found for this MSISDN over the CS Portal");
                assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().getNoResultFound(), true, "No Result Message & icon Visible", "No Result Message is not Visible"));
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - usageHistoryMenuHeaderTest" + e.fillInStackTrace(), true);
        }
    }


    @DataProviders.Table(name = "Detailed Usage History")
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "usageHistoryMenuHeaderTest"})
    public void usageHistoryMenuTest() {
        try {
            selUtils.addTestcaseDescription("Validating Usage History's  Menu of User :" + customerNumber + "validate row display data value on UI as per api response.", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), true, "Detailed Usage History Widget visible ", "Detailed Usage History Widget does not visible ", true));
            try {
                int size = Math.min(usageHistoryAPI.getTotalCount(), 20);
                final int statusCode = usageHistoryAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Usage History Widget API success and status code is :" + statusCode, "Usage History Widget API got failed and status code is :" + statusCode, false));
                if (statusCode != 200) {
                    commonLib.fail(constants.getValue("cs.get.usage.history.api.error"), false);
                    if(statusCode == 500){
                        assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().isUnableToFetch(),true, constants.getValue("usage.history.error.widget.visible"),constants.getValue("usage.history.error.widget.not.visible"),true));
                    }
                } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                    commonLib.warning("No Usage History Found for this MSISDN over the CS Portal");
                    assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().getNoResultFound(), true, "No Result Message & Icon is Visible", "No Result Message is not Visible"));
                } else {
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 1), usageHistoryAPI.getResult().get(i).getType(), " Type received is as expected on row " + i, " Type received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 2), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Date & Time received is as expected on row " + i, "Date & Time received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 3).trim(), usageHistoryAPI.getResult().get(i).getStartBalance().trim(), "Start Balance received is as expected on row " + i, "Start Balance received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 4).trim(), usageHistoryAPI.getResult().get(i).getCharges().replace("-", "").trim(), "Charges received is as expected on row " + i, "Charges received is not as expected on row " + i));
                        if (usageHistoryAPI.getResult().get(i).getCharges().charAt(0) == '-') {
                            assertCheck.append(actions.assertEqualBoolean(pages.getDetailedUsageHistoryPage().checkSignDisplay(i + 1), true, "Red Negative Symbol display at row " + i, "Red Negative Symbol does not display at row " + i));
                        }
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 5), usageHistoryAPI.getResult().get(i).getEndBalance(), "End balance received is as expected on row " + i, "End balance received is not as expected on row " + i));
                        if (usageHistoryAPI.getResult().get(i).getDescription() == null) {
                            usageHistoryAPI.getResult().get(i).setDescription("-");
                        }
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 6), usageHistoryAPI.getResult().get(i).getDescription(), "Description received is as expected on row " + i, "Description received is not as expected on row " + i));
                        if (usageHistoryAPI.getResult().get(i).getBundleName() == null) {
                            usageHistoryAPI.getResult().get(i).setBundleName("-");
                        }
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 7), usageHistoryAPI.getResult().get(i).getBundleName(), "Bundle Name received is as expected on row " + i, "Bundle Name received is not as expected on row " + i));
                    }
                }
            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                commonLib.fail("Not able to validate Detailed Usage History Completely: " + e.fillInStackTrace(), true);
            }
            pages.getUsageHistoryWidget().goingBackToHomeTab();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - usageHistoryMenuTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
