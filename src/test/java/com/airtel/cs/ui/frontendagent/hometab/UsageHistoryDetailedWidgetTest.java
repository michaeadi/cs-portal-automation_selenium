package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.UsageHistoryPOJO;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UsageHistoryDetailedWidgetTest extends Driver {
    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private UsageHistoryPOJO usageHistoryAPI;

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
            customerNumber = constants.getValue(ApplicationConstants.USAGE_HISTORY_MSISDN);
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

    @DataProviders.Table(name = "Detailed Usage History")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void usageHistoryMenuHeaderTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validate Usage history menu widget header visible and display all the Column name as per config,validate all the filter displayed,Validate all type of cdr displayed.,Validation Pagination display when data displayed on UI.", "description");
            pages.getUsageHistoryWidget().openingMoreDetails();
            Assert.assertTrue(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), "Detailed Usage History Widget does not visible ");
            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isFreeCDR(), true, "Free CDR Option does display on UI.", "Free CDR Option does not display on UI."));
            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isTypeOfCDR(), true, "Type of CDR Option does display on UI.", "Type of CDR Option does not display on UI."));
            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isTodayDateFilter(), true, "Today date filter Option does display on UI.", "Today date filter Option does not display on UI."));
            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isLast2DayDateFilter(), true, "Last 2 Days date filter Option does display on UI.", "Last 2 Days date filter Option does not display on UI."));
            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isLast7DayDateFilter(), true, "Last 7 Days date filter Option does display on UI.", "Last 7 Days date filter Option does not display on UI."));
            usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
            final int statusCode = usageHistoryAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Usage History Detailed Widget API success and status code is :" + statusCode, "Usage History Detailed Widget API got failed and status code is :" + statusCode));
            if (statusCode != 200) {
                commonLib.fail("API is Unable to Get usage history for Customer", false);
            } else if (usageHistoryAPI.getResult().size() > 0) {
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(1), data.getRow1(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(2), data.getRow2(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(3), data.getRow3(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(4), data.getRow4(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(5), data.getRow5(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(6), data.getRow6(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getHeaders(7), data.getRow7(), "Header Name for Row 7 is as expected", "Header Name for Row 7 is not as expected"));
                assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().isPagination(), true, "Pagination does display on UI", "Pagination does not display on UI"));
            } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Usage History Details from API");
                assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().getNoResultFound(), true, "No Result Message & icon Visible", "No Result Message is not Visible"));
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
            Assert.assertTrue(pages.getDetailedUsageHistoryPage().isWidgetDisplay(), "Detailed Usage History Widget does not visible ");
            try {
                int size = Math.min(usageHistoryAPI.getTotalCount(), 20);
                final int statusCode = usageHistoryAPI.getStatusCode();
                assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Usage History Widget API success and status code is :" + statusCode, "Usage History Widget API got failed and status code is :" + statusCode));
                if (statusCode != 200) {
                    commonLib.fail("API is Unable to Get usage history for Customer", false);
                } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                    commonLib.warning("Unable to get Usage History Details from API");
                    assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().getNoResultFound(), true, "No Result Message & Icon is Visible", "No Result Message is not Visible"));
                } else {
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 1), usageHistoryAPI.getResult().get(i).getType(), " Type received is as expected on row " + i, " Type received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 2), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Date & Time received is as expected on row " + i, "Date & Time received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 3).trim(), usageHistoryAPI.getResult().get(i).getStartBalance().trim(), "Start Balance received is as expected on row " + i, "Start Balance received is not as expected on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDetailedUsageHistoryPage().getValueCorrespondingToHeader(i + 1, 4).trim(), usageHistoryAPI.getResult().get(i).getCharges().trim(), "Charges received is as expected on row " + i, "Charges received is not as expected on row " + i));
                        if (usageHistoryAPI.getResult().get(i).getCharges().charAt(0) == '-') {
                            assertCheck.append(actions.assertEqual_boolean(pages.getDetailedUsageHistoryPage().checkSignDisplay(i + 1), true, "Red Negative Symbol display at row " + i, "Red Negative Symbol does not display at row " + i));
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
            pages.getUsageHistoryWidget().openingCustomerInteractionDashboard();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - usageHistoryMenuTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
