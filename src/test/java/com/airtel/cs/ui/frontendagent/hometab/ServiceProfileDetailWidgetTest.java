package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.hlrservice.HLROrderHistoryRequest;
import com.airtel.cs.model.cs.response.hlrservice.HLROrderHistoryResponse;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ServiceProfileDetailWidgetTest extends Driver {
    public static final String RUN_HLR_SERVICE_TEST_CASE = constants.getValue(ApplicationConstants.RUN_HLR_WIDGET_TEST_CASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    HLROrderHistoryResponse hlrOrderHistory;
    Boolean isPermissionEnable = false;
    Integer size=0;
    String remarks=null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkServiceProfileFlag() {
        if (!StringUtils.equals(RUN_HLR_SERVICE_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run service profile widget Test Case Flag Value is - " + RUN_TARIFF_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasPermission() {
        try {
            selUtils.addTestcaseDescription("Validate whether user has Service Profile Widget Permission ", "description");
            String permission = constants.getValue(PermissionConstants.HLR_WIDGET_PERMISSION);
            isPermissionEnable = UtilsMethods.isUserHasPermission(permission);
            assertCheck.append(actions.assertEqualBoolean(isPermissionEnable, true, "Logged in user has Service Profile Widget permission", "Logged in user doesn't have Service Profile Widget permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasPermission " + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void serviceProfileWidgetVisible() {
        try {
            selUtils.addTestcaseDescription("Validate Service Profile Widget visible or not ,Validate HLR Order History Widget visible or not  , Validate Footer and Middle Auuid ", "description");
            if (isPermissionEnable) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isServiceProfileWidgetVisible(), true, "Service Profile widget is visible ", "Service Profile widget is not visible"));
                pages.getServiceProfileDetailWidget().openServiceProfileDetailPage();
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isHLROrderHistoryTabVisible(), true, "HLR Order History tab visible ", "HLR Order History tab is not visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isHLROrderHistoryWidgetVisible(), true, "HLR Order History widget visible ", "HLR Order History widget is not visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of the HLR Order History Widget and is expected ", "Auuid NOT shown at the footer of HLR Order History Widget"));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of the HLR Order History Widget and is expected", "Auuid NOT shown at the middle of HLR Order History Widget"));
            } else
                commonLib.fail("Service Profile Widget is not visible as user has not permission to view it", true);
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - serviceProfileWidgetVisible" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testHlrOrderHistoryWidget() {
        try {
            selUtils.addTestcaseDescription("Validate HLR Order History Widget", "description");
            HLROrderHistoryRequest request = new HLROrderHistoryRequest();
            request.setMsisdn(customerNumber);
            hlrOrderHistory = api.getHLROrderHistory(request);
            assertCheck.append(actions.assertEqualIntType(hlrOrderHistory.getStatusCode(), 200, "HLR Order History API success and status code is :" + hlrOrderHistory.getStatusCode(), "HLR Order History API got failed and status code is :" + hlrOrderHistory.getStatusCode(), false));
            if (hlrOrderHistory.getStatusCode() == 200 && hlrOrderHistory.getResult().size()==0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else if (hlrOrderHistory.getStatusCode() == 500 && hlrOrderHistory.getResult().size()==0) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isWidgetErrorMessageVisible(), true, "CS API and widget both are giving error", "CS API is giving error but widget is not showing error message"));
                commonLib.fail("CS API is unable to give Hlr Order History data ", true);
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isTimestampVisible(), true, "TimeStamp is visible as expected ", "TimeStamp is not visible as expected"));
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isServiceNameVisible(), true, "Service Name is visible as expected ", "Service Name is not visible as expected"));
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isActionTypeVisible(), true, "Action Type is  visible as expected ", "Action Type is not visible as expected"));
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isCurrentStatusVisible(), true, "Current status is  visible as expected", "Current status is not visible as expected"));
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().isRemarksVisible(), true, "Remarks is  visible as expected", "Remarks is visible  not visible as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testHlrOrderHistoryWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction","testHlrOrderHistoryWidget"})
    public void checkPaginationForHLRWidget() {
        selUtils.addTestcaseDescription("Validate pagination and pagination count ,Validate User able to click on next button if rows > 10 ,After navigating to next page user should be able to navigate back using previous button ", "description");
        try {
            String paginationResult;
            size = pages.getServiceProfileDetailWidget().getNoOfRows();
            paginationResult = "1 - " + size + " of " + hlrOrderHistory.getResult().size() + " Results";
            assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getPaginationText(), paginationResult, "Pagination Count is as expected", "Pagination count is Not as  expected"));
            if (hlrOrderHistory.getResult().size() > 10) {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().checkNextBtnEnable(), true, "In pagination next button is enable as result is greater than 10", "In Pagination next button is not enable but result is greater than 10."));
                pages.getServiceProfileDetailWidget().clickNextBtn();
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().checkPreviousBtnDisable(), false, "In pagination Previous button is enable", "In Pagination previous button is not enable"));
                pages.getServiceProfileDetailWidget().clickPreviousBtn();
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getServiceProfileDetailWidget().checkNextBtnEnable(), false, "In pagination next button is disable as result is <= 10", "In Pagination next button is not disable but result is <= 10"));
            }
        actions.assertAllFoundFailedAssert(assertCheck);
    }catch (Exception e) {
            commonLib.fail("Exception in Method - checkPaginationForHLRWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction", "testHlrOrderHistoryWidget"})
    public void testHlrOrderHistoryData() {
        try {
            selUtils.addTestcaseDescription("Validate HLR Order History Widget's data ", "description");
            for (int i = 0; i < size; i++) {
                int row = i + 1;
                String date = UtilsMethods.getDateFromEpoch(hlrOrderHistory.getResult().get(i).getEventTime(),constants.getValue(CommonConstants.HLR_ORDER_HISTORY_DATE_PATTERN));
                String time = UtilsMethods.getDateFromEpochInUTC(hlrOrderHistory.getResult().get(i).getEventTime(),constants.getValue(CommonConstants.HLR_ORDER_HISTORY_TIME_PATTERN));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getHeaderValue(row, 1), date + "\n" + time.toUpperCase(), "Timestamp is as expected for row :" +row, "Timestamp is as expected for row :" +row));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getHeaderValue(row, 2).trim(), hlrOrderHistory.getResult().get(i).getTaskName(), "Service Name is as expected at row : " + row, "Service Name is NOT as expected at row : " + row));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getHeaderValue(row, 3).trim(), hlrOrderHistory.getResult().get(i).getActionType(), "Action Type is as expected at row : " + row, "Action Type is NOT as expected at row : " + row));
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getHeaderValue(row, 4).trim(), hlrOrderHistory.getResult().get(i).getStatus(), "Current Status is as expected at row : " + row, "Current Status is NOT as expected at row : " + row));
                /*if(hlrOrderHistory.getResult().get(i).getStatus().equalsIgnoreCase("COMPLETED"))
                   assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getCurrentStatusColour(row,4), "#28a745", "Colour for Current Status matched as expected at row : " + row, "Colour for Current Status Not matched as expected at row : " + row));
                if(hlrOrderHistory.getResult().get(i).getStatus().equalsIgnoreCase("FAILED"))
                    assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getCurrentStatusColour(row,4), "#dc3545", "Colour for Current Status matched as expected at row : " + row, "Colour for Current Status Not matched as expected at row : " + row));*/
                if(hlrOrderHistory.getResult().get(i).getFailureReason().isEmpty())
                remarks="-";
                assertCheck.append(actions.assertEqualStringType(pages.getServiceProfileDetailWidget().getHeaderValue(row, 5).trim(), remarks, "Remarks is as expected at row : " + row, "Remarks is NOT as expected at row : " + row));
            }
        }catch (Exception e) {
            commonLib.fail("Exception in Method - testHlrOrderHistoryData" + e.fillInStackTrace(), true);
        }
    }
}
