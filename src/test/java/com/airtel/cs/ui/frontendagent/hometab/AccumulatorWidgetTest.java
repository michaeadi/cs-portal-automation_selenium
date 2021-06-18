package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.accumulators.AccumulatorsPOJO;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AccumulatorWidgetTest extends Driver {
    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private String accumulatorWidgetIdentifier;

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteractionAPI() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.AM_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
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

    @DataProviders.Table(name = "Accumulator")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void accumulatorDetailsTest(HeaderDataBean Data) {
        try {
            selUtils.addTestcaseDescription("Validating Accumulator Details of User :" + customerNumber, "description");
            accumulatorWidgetIdentifier=pages.getDaDetailsPage().getAccumulatorId();
            try {
                assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU visible ", "Current Balance Widget MENU is not visible"));
                pages.getCurrentBalanceWidgetPage().openingDADetails();
                assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getHeaderName(accumulatorWidgetIdentifier, 0), Data.getRow1(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getHeaderName(accumulatorWidgetIdentifier, 1), Data.getRow2(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getHeaderName(accumulatorWidgetIdentifier, 2), Data.getRow3(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getHeaderName(accumulatorWidgetIdentifier, 3), Data.getRow4(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                AccumulatorsPOJO accumulatorAPI = api.accumulatorsAPITest(customerNumber);
                final int statusCode = accumulatorAPI.getStatusCode();
                assertCheck.append(actions.assertEqual_intType(statusCode, 200, "AM Profile API success and status code is :" + statusCode, "AM Profile API got failed and status code is :" + statusCode));
                if (statusCode == 200) {
                    int size = Math.min(accumulatorAPI.getResult().size(), 5);
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getColumnValue(accumulatorWidgetIdentifier, i, 0).trim(), accumulatorAPI.getResult().get(i).getId(), "Accumulator ID as received in API on row " + i, "Accumulator ID is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getColumnValue(accumulatorWidgetIdentifier, i, 1).trim(), String.valueOf(accumulatorAPI.getResult().get(i).getValue()), "Accumulator Value as received in API on row " + i, "Accumulator Value is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getColumnValue(accumulatorWidgetIdentifier, i, 2).trim(), accumulatorAPI.getResult().get(i).getStartDate() == null ? "-" : UtilsMethods.getDateFromString(accumulatorAPI.getResult().get(i).getStartDate(), constants.getValue(CommonConstants.ACCUMULATOR_UI_TIME_FORMAT), constants.getValue(CommonConstants.ACCUMULATOR_API_TIME_FORMAT)), "Accumulator Start Date as received in API on row " + i, "Accumulator Start Date is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getColumnValue(accumulatorWidgetIdentifier, i, 3).trim(), accumulatorAPI.getResult().get(i).getNextResetDate() == null ? "-" : UtilsMethods.getDateFromString(accumulatorAPI.getResult().get(i).getNextResetDate(), constants.getValue(CommonConstants.ACCUMULATOR_UI_TIME_FORMAT), constants.getValue(CommonConstants.ACCUMULATOR_API_TIME_FORMAT)), "Accumulator Next Reset Date Time as received in API on row " + i, "Accumulator Next Reset Date Time is not as received in API on row " + i));
                    }
                    pages.getDaDetailsPage().openingCustomerInteractionDashboard();
                } else {
                    commonLib.fail("API does not able to fetch accumulator details :" + accumulatorAPI.getMessage(), false);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                e.printStackTrace();
                commonLib.fail("Accumulator details does not display correctly", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - accumulatorDetailsTest" + e.fillInStackTrace(), true);
        }
    }
}
