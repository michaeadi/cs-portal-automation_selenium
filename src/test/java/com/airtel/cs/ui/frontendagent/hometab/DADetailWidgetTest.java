package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.accounts.AccountsBalance;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DADetailWidgetTest extends Driver {

    private static String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.AM_CUSTOMER_MSISDN);
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

    @DataProviders.Table(name = "Da Details")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void daDetailsTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating DA Details of User :" + customerNumber, "description");
            try {
                assertCheck.append(actions.assertEqualBoolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU visible ", "Current Balance Widget MENU is not visible"));
                pages.getCurrentBalanceWidgetPage().openingDADetails();
                assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getDaDetailsPage().getDAWidgetIdentifier()), true, "DA Detail Widget MENU visible ", "DA Detail Widget MENU is not visible",true));
                AccountsBalance accountsBalanceAPI = api.balanceAPITest(customerNumber);
                final int statusCode = accountsBalanceAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Da Details API success and status code is :" + statusCode, "Da Details API got failed and status code is :" + statusCode, false));
                if (statusCode == 200 && accountsBalanceAPI.getResult().size()>0) {
                    int size = Math.min(pages.getDaDetailsPage().getNumbersOfRows(),10);
                    commonLib.info("Total number of Rows: "+size);
                    for (int i = 0; i < data.getHeaderName().size(); i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getHeaders(i+1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                    }
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDAId(i + 1), accountsBalanceAPI.getResult().get(i).getDaId(), "DA ID display as received in API on row " + i, "DA ID is not as received in com.airtel.cs.API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDADescription(i + 1).trim(), accountsBalanceAPI.getResult().get(i).getDaDesc(), "DA Description as received in API on row " + i, "DA Description is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getBundleType(i + 1), accountsBalanceAPI.getResult().get(i).getBundleType(), "DA Bundle Type as received in API on row " + i, "DA Bundle Type is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDADateTime(i + 1), UtilsMethods.getDateFromEpochInUTC(accountsBalanceAPI.getResult().get(i).getExpiryDate(), constants.getValue(CommonConstants.DA_DETAIL_TIME_FORMAT)), "DA Date Time as received in API on row " + i, "DA Date Time is not as received in API on row " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDABalance(i + 1), accountsBalanceAPI.getResult().get(i).getCurrentDaBalance(), "DA Current Balance as received in API on row " + i, "DA Current Balance is not as received in API on row " + i));
                        if (i != 0) {
                            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isSortOrderDisplay(pages.getDaDetailsPage().getDADateTime(i + 1), pages.getDaDetailsPage().getDADateTime(i), constants.getValue(CommonConstants.DA_DETAIL_TIME_FORMAT)), true, "On UI Data display in sort order as expected.", pages.getDaDetailsPage().getDADateTime(i) + "should not display before " + pages.getDaDetailsPage().getDADateTime(i + 1)));
                        }
                    }
                    pages.getDaDetailsPage().goingBackToHomeTab();
                } else {
                    commonLib.fail("API does not able to fetch DA Details", false);
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("DA details does not display correctly", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - daDetailsTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
