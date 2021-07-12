package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.applicationutils.constants.PermissionConstants;
import com.airtel.cs.commonutils.applicationutils.enums.JavaColors;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.actiontrail.ActionTrailPOJO;
import com.airtel.cs.pojo.response.actiontrail.EventResult;
import com.airtel.cs.pojo.response.friendsfamily.FriendsFamilyPOJO;
import com.airtel.cs.pojo.response.friendsfamily.FriendsFamilyResponse;
import io.restassured.http.Headers;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;

public class FriendsFamilyWidgetTest extends Driver {
    public static final String RUN_FNF_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_FNF_WIDGET_TEST_CASE);
    private static String customerNumber;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();
    private FriendsFamilyPOJO friendsFamilyAPI;
    private final String ADD_FNF_COMMENT = "Adding new msisdn in FNF List using automation";
    private final String DELETE_FNF_COMMENT = "Deleting Newly Added member from FnF List";

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkServiceProfileFlag() {
        if (!StringUtils.equals(RUN_FNF_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("FNF Widget is NOT Enabled for this Opco=" + OPCO);
            throw new SkipException("FNF Widget is NOT Enabled for this Opco=" + OPCO);
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CURRENT_BALANCE_MSISDN);
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

    @DataProviders.Table(name = "Friends and Family")
    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void friendFamilyHeaderTest(HeaderDataBean headerValues) {
        selUtils.addTestcaseDescription("Validate Friend and Family widget header visible and display all the Column name as per config,Validate Header Name Same as mentioned in config sheet.", "description");
        try {
            //Assert.assertTrue(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget Menu is not visible");
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU is visible", "Current Balance Widget Menu is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFriendsFamilyDisplay(), true, "Friends & Family Widget display", "Friends & Family widget does not display"));
            friendsFamilyAPI = api.friendsFamilyAPITest(customerNumber);
            final int statusCode = friendsFamilyAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Friends & Family Widget API success and status code is :" + statusCode, "Friends & Family Widget API got failed and status code is :" + statusCode));
            if (statusCode != 200) {
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFWidgetErrorDisplay(), true, "API and Widget both are showing error message", "API is Giving error But Widget is not showing error Message on API is " + friendsFamilyAPI.getMessage()));
                commonLib.fail("API is Unable to Get friends & Family History for Customer", false);
            } else if (friendsFamilyAPI.getResult().get(0).getFafInformation().size() > 0) {
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(1).toLowerCase().trim(), headerValues.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(2).toLowerCase().trim(), headerValues.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(3).toLowerCase().trim(), headerValues.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(4).toLowerCase().trim(), headerValues.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(5).toLowerCase().trim(), headerValues.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFriendsFamilyHeaders(6).toLowerCase().trim(), headerValues.getRow6().toLowerCase().trim(), "Header Name for Row 6 is as expected", "Header Name for Row 6 is not as expected"));
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFNoResultIconDisplay(), true, "API and Widget both are showing No Result found Icon", "API is Giving no result But Widget is not showing no result found icon"));
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - friendFamilyHeaderTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void isUserHasFriendFamilyPermission() {
        try {
            selUtils.addTestcaseDescription("Verify that friends & family widget should be visible to the logged in agent if fnf permission is enabled in UM, Check User has permission to view friends & family Widget Permission", "description");
            String fnf_permission = constants.getValue(PermissionConstants.FRIENDS_FAMILY_WIDGET_PERMISSION);
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFriendsFamilyDisplay(), UtilsMethods.isUserHasPermission(new Headers(map), fnf_permission), "Friends and Family Widget displayed correctly as per user permission", "Friends and Family Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasFriendFamilyPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProviderClass = DataProviders.class, dependsOnMethods = {"openCustomerInteraction"})
    public void validateFriendsFamilyWidget() {
        try {
            selUtils.addTestcaseDescription("Verify friends & family Widget with customer number: " + customerNumber + ",Check friends & family giving response without fail,Validate widget data detail as per api response", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFriendsFamilyDisplay(), true, "Friends & Family Widget display", "Friends & Family widget does not display"));
            friendsFamilyAPI = api.friendsFamilyAPITest(customerNumber);
            final int statusCode = friendsFamilyAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Friends & Family Widget API success and status code is :" + statusCode, "Friends & Family Widget API got failed and status code is :" + statusCode));
            if (statusCode == 200) {
                if (friendsFamilyAPI.getResult().get(0).getFafInformation().isEmpty() | friendsFamilyAPI.getResult() == null) {
                    commonLib.warning("Unable to get Last Service Profile from API");
                    assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFNoResultIconDisplay(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getFnFNoResultMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    ArrayList<FriendsFamilyResponse> fnfInfoAPI = friendsFamilyAPI.getResult().get(0).getFafInformation();
                    int size = Math.min(fnfInfoAPI.size(), 5);
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 1), fnfInfoAPI.get(i).getFafNumber(), "FNF Number is As received in API for row number " + i, "FNF Number is not As received in API for row number " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 2), fnfInfoAPI.get(i).getOwner(), "Owner is As received in API for row number " + i, "Owner is not As received in API for row number " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 3), fnfInfoAPI.get(i).getStartDate(), "Start date is As received in API for row number " + i, "Start date is not As received in API for row number " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 4), fnfInfoAPI.get(i).getEndDate(), "End date is As received in API for row number " + i, "End date is not As received in API for row number " + i));
                        assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 5), fnfInfoAPI.get(i).getFafGroup(), "FnF indicator is As received in API for row number " + i, "FnF indicator is not As received in API for row number " + i));
                        assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isActionIconOnFriendsFamily(i + 1), true, "Action Icon display as expected for row " + i, "Action Icon does not display as expected for row " + i));
                    }
                }
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFWidgetErrorDisplay(), true, "API is Giving error and Widget is showing error icon", "API is Giving error But Widget is not showing error as expected"));
                commonLib.fail("API is unable to fetch Friends & family History ", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateFriendsFamilyWidget" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasFriendFamilyPermission", "openCustomerInteraction"})
    public void isUserHasFriendFamilyPermissionToAddMember() {
        try {
            selUtils.addTestcaseDescription("Verify that agent having the UM permission Add FnF should be able to add FnF, Validate the user have permission to add new member, Validate if user has permission then Add member Icon must display on Widget", "description");
            String fnf_add_permission = constants.getValue(PermissionConstants.FRIENDS_FAMILY_ADD_MEMBER);
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isFnFAddMemberIcon(), UtilsMethods.isUserHasPermission(new Headers(map), fnf_add_permission), "Friends and Family Add Member Icon on Widget displayed correctly as per user permission", "Friends and Family Add Member Icon on Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasFriendFamilyPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasFriendFamilyPermissionToAddMember", "openCustomerInteraction"})
    public void addMemberToFNF() {
        try {
            selUtils.addTestcaseDescription("Verify for adding a number into Customer's FnF list,Click on Add member Icon, Validate Add member pop up open, validate user have option to add new member,Add new Member into FnF List,Click on Submit button,Validate newly added member show in list,", "description");
            pages.getDaDetailsPage().clickAddMemberIcon();
            try {
                assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getPopUpTitle().toLowerCase().trim(), "add fnf number", "Open Pop up title as expected", "Open Pop up title as not expected"));
                pages.getDaDetailsPage().enterAddFnfNumber(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN));
                pages.getAuthTabPage().enterComment(ADD_FNF_COMMENT);
                assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isSubmitBtnEnable(), true, "Submit button enable after adding msisdn & comment", "Submit button does not enable after adding msisdn & comment"));
                pages.getAuthTabPage().clickSubmitBtn();
                try {
                    commonLib.pass("Reading Message: " + pages.getTemplateManagement().readResponseMessage());
                } catch (NoSuchElementException | TimeoutException e) {
                    commonLib.infoColored("Not able to read Message Pop up: " + e.getMessage(), JavaColors.BLUE, true);
                }
                if (pages.getAuthTabPage().isSIMBarPopup()) {
                    commonLib.fail("Get Failure message as pop up does not closed after clicking on submit button. Error Message: " + pages.getAuthTabPage().getErrorMessage(), true);
                    pages.getDaDetailsPage().closePopup();
                } else {
                    friendsFamilyAPI = api.friendsFamilyAPITest(customerNumber);
                    boolean flag = true;
                    for (int i = 0; i < friendsFamilyAPI.getResult().get(0).getFafInformation().size(); i++) {
                        if (friendsFamilyAPI.getResult().get(0).getFafInformation().get(i).getFafNumber().equals(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN))) {
                            commonLib.pass("New Number added successfully into FnF List.");
                            flag = false;
                            break;
                        }
                    }
                    if (flag) {
                        commonLib.fail("Not able to add new number into FnF List", true);
                    }
                }
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("Not able to add new number: " + e.fillInStackTrace(), true);
                pages.getDaDetailsPage().closePopup();
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - addMemberToFNF" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"addMemberToFNF", "openCustomerInteraction"})
    public void validateActionTabAfterAddFnFMember() {
        try {
            selUtils.addTestcaseDescription("Verify Action trail tab after adding number into Customer's FnF list,Hit action trail event api, Validate action type & comments & agent id", "description");
            ActionTrailPOJO actionTrailAPI = api.getEventHistory(customerNumber, "ACTION");
            final int statusCode = actionTrailAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Friends & Family Widget API success and status code is :" + statusCode, "Friends & Family Widget API got failed and status code is :" + statusCode));
            if (statusCode == 200) {
                EventResult actionResultAPI = actionTrailAPI.getResult().get(0);
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getActionType(), constants.getValue(CommonConstants.ADD_FNF_ACTION_TYPE), "Action Type of Add FnF as expected", "Action Type of Add FnF as not expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getComments(), ADD_FNF_COMMENT, "Comment same as expected.", "Comment same as not expected."));
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getAgentId(), constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID), "Agent id same as expected", "Agent id same as not expected"));
            } else {
                commonLib.fail("Not able to fetch action trail event log using API", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateActionTabAfterAddFnFMember" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 8, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasFriendFamilyPermission", "openCustomerInteraction"})
    public void isUserHasFriendFamilyPermissionToDeleteMember() {
        try {
            selUtils.addTestcaseDescription("Verify that agent having the UM permission delete FnF should be able to delete FnF, Validate the user have permission to delete existing member, Validate if user has permission then delete member icon must display on Widget", "description");
            String fnf_delete_permission = constants.getValue(PermissionConstants.FRIENDS_FAMILY_DELETE_MEMBER);
            assertCheck.append(actions.assertEqual_boolean(pages.getDaDetailsPage().isActionIconOnFriendsFamily(1), UtilsMethods.isUserHasPermission(new Headers(map), fnf_delete_permission), "Friends and Family delete Member action button on Widget displayed correctly as per user permission", "Friends and Family delete Member action button on Widget does not display correctly as per user permission"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - isUserHasFriendFamilyPermission" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 9, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"isUserHasFriendFamilyPermissionToDeleteMember", "validateFriendsFamilyWidget", "openCustomerInteraction"})
    public void deleteMemberToFnF() {
        try {
            selUtils.addTestcaseDescription("Verify for adding a number into Customer's FnF list,Click on Add member Icon, Validate Add member pop up open, validate user have option to add new member,Add new Member into FnF List,Click on Submit button,Validate newly added member show in list,", "description");
            ArrayList<FriendsFamilyResponse> fnfInfoAPI = friendsFamilyAPI.getResult().get(0).getFafInformation();
            int size = Math.min(fnfInfoAPI.size(), 5);
            boolean status = true;
            for (int i = 0; i < size; i++) {
                if (pages.getDaDetailsPage().getValueCorrespondingToFriendsFamily(i + 1, 1).equalsIgnoreCase(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN))) {
                    pages.getDaDetailsPage().clickActionIconOnFriendsFamily(i + 1);
                    try {
                        assertCheck.append(actions.assertEqual_stringType(pages.getDaDetailsPage().getPopUpTitle().toLowerCase().trim(), "delete fnf number", "Open Pop up title as expected", "Open Pop up title as not expected"));
                        pages.getAuthTabPage().enterComment(DELETE_FNF_COMMENT);
                        assertCheck.append(actions.assertEqual_boolean(pages.getAuthTabPage().isSubmitBtnEnable(), true, "Submit button enable after adding msisdn & comment", "Submit button does not enable after adding msisdn & comment"));
                        pages.getAuthTabPage().clickSubmitBtn();
                        try {
                            commonLib.pass("Reading Message: " + pages.getTemplateManagement().readResponseMessage());
                        } catch (NoSuchElementException | TimeoutException e) {
                            commonLib.infoColored("Not able to read Message Pop up: " + e.getMessage(), JavaColors.BLUE, true);
                        }
                        if (pages.getAuthTabPage().isSIMBarPopup()) {
                            commonLib.fail("Get Failure message as pop up does not closed after clicking on submit button. Error Message: " + pages.getAuthTabPage().getErrorMessage(), true);
                        } else {
                            friendsFamilyAPI = api.friendsFamilyAPITest(customerNumber);
                            boolean flag = true;
                            for (int j = 0; j < friendsFamilyAPI.getResult().get(0).getFafInformation().size(); j++) {
                                if (friendsFamilyAPI.getResult().get(0).getFafInformation().get(j).getFafNumber().equals(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN))) {
                                    commonLib.fail("After deleting Newly added Number still showing into FnF List.", true);
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                commonLib.pass(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN) + " :- Remove from FnF List successfully.");
                            }
                        }
                    } catch (NoSuchElementException | TimeoutException e) {
                        commonLib.fail("Not able to delete member: " + e.fillInStackTrace(), true);
                        pages.getDaDetailsPage().closePopup();
                    }
                    status = false;
                    break;
                }
            }
            if (status) {
                commonLib.fail(constants.getValue(ApplicationConstants.FNF_NEW_MEMBER_MSISDN) + ":- MSISDN does not added into FnF List as expected.", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - deleteMemberToFnF" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 10, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"addMemberToFNF", "openCustomerInteraction"})
    public void validateActionTabAfterDeleteFnFMember() {
        try {
            selUtils.addTestcaseDescription("Verify Action trail tab after removing number into Customer's FnF list,Hit action trail event api, Validate action type & comments & agent id", "description");
            ActionTrailPOJO actionTrailAPI = api.getEventHistory(customerNumber, "ACTION");
            final int statusCode = actionTrailAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Friends & Family Widget API success and status code is :" + statusCode, "Friends & Family Widget API got failed and status code is :" + statusCode));
            if (statusCode == 200) {
                EventResult actionResultAPI = actionTrailAPI.getResult().get(0);
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getActionType(), constants.getValue(CommonConstants.DELETE_FNF_ACTION_TYPE), "Action Type of Remove FnF as expected", "Action Type of Remove FnF as not expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getComments(), DELETE_FNF_COMMENT, "Comment same as expected.", "Comment same as not expected."));
                assertCheck.append(actions.matchUiAndAPIResponse(actionResultAPI.getAgentId(), constants.getValue(CommonConstants.ALL_USER_ROLE_AUUID), "Agent id same as expected", "Agent id same as not expected"));
            } else {
                commonLib.fail("Not able to fetch action trail event log using API", true);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateActionTabAfterDeleteFnFMember" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }


}
