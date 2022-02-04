package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.hlrservice.HLROrderHistoryRequest;
import com.airtel.cs.model.response.hlrservice.HLROrderHistoryResponse;
import com.airtel.cs.model.response.hlrservice.HLRService;
import com.airtel.cs.pagerepository.pagemethods.ServiceClassWidget;
import io.restassured.http.Headers;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.NoSuchElementException;

@Log4j2
public class ServiceProfileWidgetTest extends Driver {

    public static final String RUN_HLR_SERVICE_TEST_CASE = constants.getValue(ApplicationConstants.RUN_HLR_WIDGET_TEST_CASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();
    private HLRService hlrService;
    private ServiceClassWidget serviceClassWidget;

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
            throw new SkipException("Skipping because this functionality does not applicable for current opco");
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasHLRPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that Service Profile widget should be visible to the logged in agent if HLR permission is enabled in UM, Check User has permission to view HLR Widget Permission", "description");
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isServiceClassWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), "Service Profile Widget displayed correctly as per user permission", "Service Profile Widget does not display correctly as per user permission"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasHLRPermission" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"RegressionTest", "SmokeTest"}, enabled = false, dependsOnMethods = {"openCustomerInteraction"})
    public void userHasNoHLRPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that Service Profile widget should not be visible to the logged in agent if HLR permission is disabled in UM", "description");
            pages.getLoginPage().openNewTempBrowserAndLoginInUM();
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_PERMISSION);
            pages.getUserManagementPage().removeOrAddPermission(constants.getValue(PermissionConstants.HLR_WIDGET_PERMISSION_UM));
            pages.getUserManagementPage().destroyTempBrowser();
            assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isServiceClassWidgetDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), "Service Profile Widget must not displayed as permission have removed", "Service Profile Widget is still displayed as permission have removed"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasHLRPermission" + e.fillInStackTrace(), true);
        }
    }


    @DataProviders.Table(name = "Service Profile")
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void validateServiceProfileWidget(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Verify Service Profile Widget with customer number: " + customerNumber + ",Check Service Profile giving response without fail,Validate widget header display as per config,Validate widget data detail as per api response", "description");
            commonLib.info("Opening URL");
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceClassWidgetDisplay(), true, "Service Profile Widget displayed correctly", "Service Profile Widget does not display correctly"));
            hlrService = api.getServiceProfileWidgetInfo(customerNumber);
            final int statusCode = hlrService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Service Profile API success and status code is :" + statusCode, "HLR Service Profile API got failed and status code is :" + statusCode, false));
            if (statusCode == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    int size = Math.min(hlrService.getTotalCount(), 5);
                    for (int i = 0; i < data.getHeaderName().size(); i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(widgetMethods.getHeaderName(pages.getServiceClassWidget().getUniqueIdentifier(),i), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected",true));
                    }
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.assertEqualStringType(serviceClassWidget.getValueCorrespondingToServiceProfile(i + 1, 1), hlrService.getResult().get(i).getServiceName(), "Service Name is As received in API for row number " + i, "Service Name is not As received in API for row number " + i,true,true));
                        assertCheck.append(actions.assertEqualStringType(serviceClassWidget.getValueCorrespondingToServiceProfile(i + 1, 2), hlrService.getResult().get(i).getServiceDesc(), "Service desc is As received in API for row number " + i, "Service desc is not As received in API for row number " + i));
                        if (hlrService.getResult().get(i).getHlrCodes().size() > 0)
                            assertCheck.append(actions.assertEqualStringType(serviceClassWidget.getValueCorrespondingToServiceProfile(i + 1, 3), hlrService.getResult().get(i).getHlrCodes().get(0), "HLR Code is As received in API for row number " + i, "HLR Code is not As received in API for row number " + i));
                        if (hlrService.getResult().get(i).getHlrCodeDetails().size() > 0)
                            assertCheck.append(actions.assertEqualStringType(serviceClassWidget.getValueCorrespondingToServiceProfile(i + 1, 4), hlrService.getResult().get(i).getHlrCodeDetails().get(0), "HLR code details is As received in API for row number " + i, "HLR code details is not As received in API for row number " + i));
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.getServiceStatus(i + 1), true, "Service Status is as expected", "Service Status is not as expected"));
                            } else {
                                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.getServiceStatus(i + 1), false, "Service Status is as expected", "Service Status is not as expected"));
                            }
                        }
                    }
                }

            } else {
                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateServiceProfileWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void checkPaginationForHLRWidget() {
        selUtils.addTestcaseDescription("Validate Offers widget display pagination and agent able to navigate through pagination, Validate Pagination counting display correctly, validate User able to click on next button if rows >5,After navigate tp next page user able to navigate back using previous button ", "description");
        try {
            serviceClassWidget = pages.getServiceClassWidget();
            String paginationResult=null;
            if (hlrService.getResult().isEmpty() || hlrService.getResult() == null)
                assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Results found", "Error Message is as expected", "Error Message is not as expected"));
            else {
                paginationResult = "1 - 5 of " + hlrService.getResult().size() + " Results";
                assertCheck.append(actions.assertEqualStringType(pages.getServiceClassWidget().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
                if (hlrService.getResult().size() > 5) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().checkNextBtnEnable(), true, "In pagination next button is enable as result is greater than 5", "In Pagination next button is not enable but result is greater than 5."));
                    pages.getServiceClassWidget().clickNextBtn();
                    assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().checkPreviousBtnDisable(), false, "In pagination Previous button is enable", "In Pagination previous button is not enable"));
                    pages.getServiceClassWidget().clickPreviousBtn();
                    assertCheck.append(actions.assertEqualStringType(pages.getServiceClassWidget().getPaginationText(), paginationResult, "Pagination Count as expected", "Pagination count as not expected"));
                } else {
                    assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().checkNextBtnEnable(), false, "In pagination next button is disable as result is <= 5", "In Pagination next button is not disable but result is <= 5."));
                }
            }actions.assertAllFoundFailedAssert(assertCheck);
        }catch (Exception e) {
            commonLib.fail("Exception in Method - checkPaginationForHLRWidget" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateUserHasPermissionToBar() {
        try {
            selUtils.addTestcaseDescription("Verify that agent having the UM permission should be able to bar any of the unbarred service, Validate Pop up window after clicking on action button", "description");
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_BAR_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), true, "User has permission to bar service as expected", "User does not has permission to bar service as expected."));
            int size = Math.min(hlrService.getTotalCount(), 5);
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            boolean flag = true;
            final int statusCode = hlrService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Service Profile API success and status code is :" + statusCode, "HLR Service Profile API got failed and status code is :" + statusCode, false));
            if (statusCode == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    for (int i = 0; i < size; i++) {
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                pages.getServiceClassWidget().clickServiceStatus(i + 1);
                                assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isBarTitleVisible(), true, "Service class unbar pop up window displayed as expected", "Service class unbar pop up window does not displayed as expected"));
                                pages.getServiceClassWidget().closePopupWindow();
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        commonLib.warning("No transaction found in service class widget to bar the status.");
                    }
                }
            } else {
                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasHLRPermission" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateUserHasPermissionToUnBar() {
        try {
            selUtils.addTestcaseDescription("Verify that agent having the UM permission should be able to unbar any of the barred service, Validate Pop up window after clicking on action button", "description");
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_UNBAR_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), true, "User has permission to bar service as expected", "User does not has permission to bar service as expected."));
            int size = Math.min(hlrService.getTotalCount(), 5);
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            boolean flag = true;
            final int statusCode = hlrService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Service Profile API success and status code is :" + statusCode, "HLR Service Profile API got failed and status code is :" + statusCode, false));
            if (statusCode == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    for (int i = 0; i < size; i++) {
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (!hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                pages.getServiceClassWidget().clickServiceStatus(i + 1);
                                assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isUnBarTitleVisible(), true, "Service class unbar pop up window displayed as expected", "Service class unbar pop up window does not displayed as expected"));
                                pages.getServiceClassWidget().closePopupWindow();
                                flag = false;
                                break;
                            }
                        }
                    }
                    if (flag) {
                        commonLib.warning("No transaction found in service class widget to unbar the status.");
                    }
                }
            } else {
                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateUserHasPermissionToUnBar" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 8, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void changeServiceStatusTestToBarred() {
        try {
            selUtils.addTestcaseDescription("Verify that on barring any service, the HLR widget should refresh and show that the particular service has been barred.", "description");
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_UNBAR_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), true, "User has permission to bar service as expected", "User does not has permission to bar service as expected."));
            int size = Math.min(hlrService.getTotalCount(), 5);
             serviceClassWidget = pages.getServiceClassWidget();
            boolean flag = true;
            final int statusCode = hlrService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Service Profile Profile API success and status code is :" + statusCode, "HLR Service Profile API got failed and status code is :" + statusCode, false));
            if (statusCode == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    for (int i = 0; i < size; i++) {
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                pages.getServiceClassWidget().clickServiceStatus(i + 1);
                                String popUpTitle = hlrService.getResult().get(i).getServiceName().trim().toUpperCase() + " BARRING";
                                assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isBarTitleVisible(popUpTitle), true, "Service class bar pop up window displayed as expected", "Service class unbar pop up window does not displayed as expected"));
                                pages.getServiceClassWidget().enterComment("Service Status Changed to Barred Using Automation");
                                pages.getServiceClassWidget().clickSubmitBtn();
                                try {
                                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.getServiceStatus(i + 1), false, "Service Status is as expected at row " + (i + 1), "Service Status is not as expected at row " + (i + 1)));
                                    flag = false;
                                    break;
                                } catch (NoSuchElementException | TimeoutException e) {
                                    commonLib.fail("Not able to change service status, due to api error", true);
                                    pages.getServiceClassWidget().closePopupWindow();
                                }
                            }
                        }
                    }
                    if (flag) {
                        commonLib.warning("No transaction found in service class widget to bar the status.");
                    }
                }
            } else {
                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - changeServiceStatusTestToBarred" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 9, groups = {"RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void changeServiceStatusTestToUNBarred() {
        try {
            selUtils.addTestcaseDescription("Verify that on barring any service, the HLR widget should refresh and show the the particular service has been barred.", "description");
            String hlr_permission = constants.getValue(PermissionConstants.HLR_WIDGET_UNBAR_PERMISSION);
            assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isUserHasPermission(new Headers(map), hlr_permission), true, "User have permission to bar service as expected", "User does not have permission to bar service as expected."));
            int size = Math.min(hlrService.getTotalCount(), 5);
            final ServiceClassWidget serviceClassWidget = pages.getServiceClassWidget();
            boolean flag = true;
            final int statusCode = hlrService.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Service Profile Profile API success and status code is :" + statusCode, "HLR Service Profile API got failed and status code is :" + statusCode, false));
            if (statusCode == 200) {
                if (hlrService.getResult().isEmpty() || hlrService.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    for (int i = 0; i < size; i++) {
                        if (hlrService.getResult().get(i).getType().equalsIgnoreCase("Action")) {
                            if (!hlrService.getResult().get(i).getServiceStatus().equalsIgnoreCase("enabled")) {
                                pages.getServiceClassWidget().clickServiceStatus(i + 1);
                                String popUpTitle = hlrService.getResult().get(i).getServiceName().trim().toUpperCase() + " UNBARRING";
                                assertCheck.append(actions.assertEqualBoolean(pages.getServiceClassWidget().isUnBarTitleVisible(popUpTitle), true, "Service class unbar pop up window displayed as expected", "Service class unbar pop up window does not displayed as expected"));
                                pages.getServiceClassWidget().enterComment("Service Status Changed to Unbarred Using Automation");
                                pages.getServiceClassWidget().clickSubmitBtn();
                                try {
                                    assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.getServiceStatus(i + 1), true, "Service Status is as expected at row " + (i + 1), "Service Status is not as expected at row " + (i + 1)));
                                    flag = false;
                                    break;
                                } catch (NoSuchElementException | TimeoutException e) {
                                    commonLib.fail("Not able to change service status, due to api error", true);
                                    pages.getServiceClassWidget().closePopupWindow();
                                }
                            }
                        }
                    }
                    if (flag) {
                        commonLib.warning("No transaction found in service class widget to unbar the status.");
                    }
                }
            } else {
                assertCheck.append(actions.assertEqualBoolean(serviceClassWidget.isServiceProfileErrorVisible(), true, "com.airtel.cs.API is Giving error But Widget is showing error Message on com.airtel.cs.API is", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("API is unable to fetch Service Profile History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - changeServiceStatusTestToUNBarred" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void hlrOrderHistoryTest() {
        try {
            selUtils.addTestcaseDescription("Validate HLR Order History Test ", "description");
            if (hlrService.getResult().isEmpty() || hlrService.getResult() == null)
                assertCheck.append(actions.assertEqualStringType(serviceClassWidget.gettingServiceProfileNoResultFoundMessage(), "No Results found", "Error Message is as expected", "Error Message is not as expected"));
            HLROrderHistoryRequest request = new HLROrderHistoryRequest();
            request.setMsisdn(constants.getValue(ApplicationConstants.CUSTOMER_MSISDN));
            request.setPageNumber(0);
            request.setPageSize(10);
            HLROrderHistoryResponse response = api.getHLROrderHistory(request);
            final int statusCode = response.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "HLR Order History API success and status code is :" + statusCode, "HLR Order History API got failed and status code is :" + statusCode, false));
            final String status = response.getStatus();
            final int pageNumber = response.getPageNumber();
            final int pageSize = response.getPageSize();
            final Integer totalCount = response.getTotalCount();
            final int resultSize = response.getResult().size();
            if (statusCode == 200) {
                assertCheck.append(actions.assertEqualStringNotNull(status, "HLR Order History API status null check pass" , "HLR Order History API status null check fail"));
                assertCheck.append(actions.assertEqualIntType(pageNumber, request.getPageNumber(), "HLR Order History API pageNumber is matched and pageNumber is : " + pageNumber, "HLR Order History API pageNumber is not matched and pageNumber is : " + pageNumber, false));
                assertCheck.append(actions.assertEqualIntType(pageSize, request.getPageSize(), "HLR Order History API pageSize is matched and pageSize is : " + pageSize, "HLR Order History API pageSize is not matched and pageSize is : " + pageSize, false));
                assertCheck.append(actions.assertEqualIntNotNull(totalCount, "HLR Order History API totalCount null check pass", "HLR Order History API totalCount null check fail"));
                assertCheck.append(actions.assertEqualIntType(resultSize, request.getPageSize(), "HLR Order History API resultSize is matched and resultSize is : " + resultSize, "HLR Order History API resultSize is not matched and resultSize is : " + resultSize, false));
            } else {
                commonLib.fail("HLR Order History API Response is not 200", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - hlrOrderHistoryTest " + e.fillInStackTrace(), true);
        }
    }
}
