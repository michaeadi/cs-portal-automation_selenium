package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.kycprofile.KYCProfile;
import io.restassured.http.Headers;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DetailAccountInfoTest extends Driver {
    private static String customerNumber = null;
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
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
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
     * To-do : VIEW_POSTPAID_BILL
     */
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasDetailedAccountInformationPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that detailed account information widget should be visible to the logged in agent if account info permission is enabled in UM, Check User has permission to view detailed account information Widget Permission", "description");
            String detailedAccountInfo_permission = constants.getValue(PermissionConstants.VIEW_POSTPAID_BILL);
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isDetailedAccountInformationWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), detailedAccountInfo_permission), "Detailed Account Information Widget displayed correctly as per user permission", "Detailed Account Information Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasDetailedAccountInformationPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    /**
     * This method is used to show view bill on the basis of connection type and UM permission
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void connectionTypeAndUMPermissionTest() {

        try {
            selUtils.addTestcaseDescription("Verify that view bill should be visible to the logged in agent on the basis of connection type and UM permission", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_POSTPAID_MSISDN);
            KYCProfile kycProfile = api.kycProfileAPITest(customerNumber);
            final Integer statusCode = kycProfile.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "KYC Profile API Status Code Matched and is :" + statusCode, "KYC Profile API Status Code NOT Matched and is :" + statusCode));
            String connectionType = pages.getDemoGraphicPage().getConnectionType().toUpperCase().trim();
            final boolean umAccountInformationPermission = pages.getAccountInformationWidget().isAccountInformationWidgetDisplay();
            final boolean umViewBillPermission = pages.getDetailAccountInfoWidget().isDetailedAccountInformationWidgetDisplay();
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getConnectionType().toLowerCase().trim(),
                    kycProfile.getResult().getLineType().toLowerCase().trim(), "Customer Connection Type is as expected",
                    "Customer Connection Type as not expected"));
            if (connectionType.equalsIgnoreCase("POSTPAID") && umAccountInformationPermission && umViewBillPermission) {
                assertCheck.append(actions.assertEqualBoolean(pages.getAccountInformationWidget().isAccountInformationWidgetDisplay(), true, "View bill is visible", "View bill not visible"));
            } else {
                commonLib.fail(" Plan and Pack widget is not displayed", true);
            }

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - connectionTypeAndUMPermissionTest" + e.fillInStackTrace(), true);
        }

    }

    /**
     * This method is used to show account info detailed icon
     */
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void accountInfoIcon(){
        try{
            selUtils.addTestcaseDescription("Verify that detailed account info icon should be visible to the logged in agent", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDetailAccountInfoWidget().isActionIconVisibleOnAccountInfo(), true, "Detailed account information icon visible", "Detailed account information icon not visible"));
            pages.getDetailAccountInfoWidget().openAccountInformationDetailPage();
            assertCheck.append(actions.assertEqualStringType(pages.getDetailAccountInfoWidget().getAccountInfoDetailWidget().toUpperCase(), "ACCOUNT INFORMATION DETAIL", "Account Information Detail display as expected in detailed account info", "Account Information Detail not display as expected in detailed account info"));
            actions.assertAllFoundFailedAssert(assertCheck);
        }catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in Method - accountInfoIcon()" + e.fillInStackTrace(), true);
        }
    }

}