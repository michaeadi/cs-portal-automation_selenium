package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.accounts.AccountsBalance;
import com.airtel.cs.model.cs.response.adjustmenthistory.AdjustmentHistory;
import com.airtel.cs.model.cs.response.adjustmenthistory.AdjustmentResult;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HbbWidgetsVisibilityTest extends Driver {

    private static String hbbCustomerNumber = null;
    private static String adjustmentReason;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest",})
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
            hbbCustomerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(hbbCustomerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "HBB Page Loaded Successfully", "HBB Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void widgetsVisibility() {
        try {
            selUtils.addTestcaseDescription("Validating Current Plan,DA Details,Recharge History,Usage History, Service Profile Widgets", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isCurrentPlanVisible(), true, "Current Plan is successfully displayed ", "Current Plan is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isRechargeHistoryVisible(), true, "Recharge History is successfully displayed ", "Recharge History is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isUsageHistoryVisible(), true, "Usage History is successfully displayed ", "Usage History is not displayed"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isServiceProfileVisible(), true, "Service Profile is successfully displayed ", "Service Profile is not displayed"));

            selUtils.addTestcaseDescription("Validating Send InternetSetting, Send SMS tab", "description");
            pages.getCustomerProfilePage().clickOnAction();
            assertCheck.append(actions.assertEqualBoolean(pages.getCustomerProfilePage().isSendInternetSettingTitleVisible(), false, "Send Internet Settings is not visible  ", "Send Internet Settings is visible"));
            assertCheck.append(actions.assertEqualBoolean(pages.getHbbProfilePage().isSendSmsVisible(), true, "Send SMS is visible  ", "Send SMS is not visible"));
            pages.getCustomerProfilePage().clickOutside();

            selUtils.addTestcaseDescription("Validating footer Auuid and middle Auuid", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getHbbProfilePage().getFooterAuuid(), loginAUUID, "Auuid shown at the footer of Hbb Profile and is correct", "Auuid not shown at the footer of Hbb Profile"));
            assertCheck.append(actions.assertEqualStringType(pages.getHbbProfilePage().getMiddleAuuid(), loginAUUID, "Auuid shown at the middle of Hbb Profile and is correct", "Auuid not shown at the footer of Hbb Profile"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - widgetsVisibility" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void testActivePacksTab() {
        try {
            selUtils.addTestcaseDescription("Validating DA Details of User :" + hbbCustomerNumber, "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetMenuVisible(), true, "Current Balance Widget MENU visible ", "Current Balance Widget MENU is not visible"));
            pages.getCurrentBalanceWidgetPage().openingDADetails();
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getDaDetailsPage().getDAWidgetIdentifier()), true, "DA Detail Table is  visible ", "DA Detail Table is not visible", true));
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getDaDetailsPage().getAccumulatorId()), true, "Accumulators  Table is  visible ", "Accumulators Table is not visible", true));
            assertCheck.append(actions.assertEqualBoolean(widgetMethods.isWidgetVisible(pages.getDaDetailsPage().getOfferWidgetIdentifier()), true, "Offer Table is  visible ", "Offer Table is not visible", true));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testActivePacksTab" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @DataProviders.Table(name = "Da Details")
    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "testActivePacksTab")
    public void testDADetailWidgetData(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validate Da Detailed Widget data with API result, API used is HBB Query Balance V2", "description");
            final AccountsBalance hbbQueryBalance = api.getHBBQueryBalance(hbbCustomerNumber);
            final int statusCode = hbbQueryBalance.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Da Details API success and status code is :" + statusCode, "Da Details API got failed and status code is :" + statusCode, false));
            if (statusCode == 200 && hbbQueryBalance.getResult().size() > 0) {
                int size = Math.min(pages.getDaDetailsPage().getNumbersOfRows(), 10);
                commonLib.info("Total number of Rows: " + size);
                for (int i = 0; i < data.getHeaderName().size(); i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getHeaders(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                }
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDAId(i + 1), hbbQueryBalance.getResult().get(i).getDaId(), "DA ID display as received in API on row " + i, "DA ID is not as received in com.airtel.cs.API on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDADescription(i + 1).trim(), hbbQueryBalance.getResult().get(i).getDaDesc(), "DA Description as received in API on row " + i, "DA Description is not as received in API on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getBundleType(i + 1), hbbQueryBalance.getResult().get(i).getBundleType(), "DA Bundle Type as received in API on row " + i, "DA Bundle Type is not as received in API on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDADateTime(i + 1), UtilsMethods.getDateFromEpochInUTC(hbbQueryBalance.getResult().get(i).getExpiryDate(), constants.getValue(CommonConstants.DA_DETAIL_TIME_FORMAT)), "DA Date Time as received in API on row " + i, "DA Date Time is not as received in API on row " + i));
                    assertCheck.append(actions.matchUiAndAPIResponse(pages.getDaDetailsPage().getDABalance(i + 1), hbbQueryBalance.getResult().get(i).getCurrentDaBalance(), "DA Current Balance as received in API on row " + i, "DA Current Balance is not as received in API on row " + i));
                    if (i != 0) {
                        assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isSortOrderDisplay(pages.getDaDetailsPage().getDADateTime(i + 1), pages.getDaDetailsPage().getDADateTime(i), constants.getValue(CommonConstants.DA_DETAIL_TIME_FORMAT)), true, "On UI Data display in sort order as expected.", pages.getDaDetailsPage().getDADateTime(i) + "should not display before " + pages.getDaDetailsPage().getDADateTime(i + 1)));
                    }
                }
                pages.getDaDetailsPage().goingBackToHomeTab();
            } else {
                commonLib.fail("API does not able to fetch DA Details", false);
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - testDADetailWidgetData" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"RegressionTest", "SanityTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void validateAdjustmentCreditType() {
        try {
            selUtils.addTestcaseDescription("Validate user able to open adjustment tab through home action,Validate agent able to choose fields reason and type and enter comment,Validate admin able to put comment,Validate agent able to click submit button and agent must receive proper success message", "description");
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), true, "Credit type adjustment permitted by the agent as expected", "Credit type adjustment does not permitted as per user permission", true));
            pages.getCustomerProfilePage().clickOnAction();
            Boolean visible = pages.getCustomerProfilePage().checkAdjustmentAction();
            if (visible) {
                pages.getCustomerProfilePage().clickAdjustment();
                pages.getAdjustmentTabPage().OpenAdjustmentReason();
                pages.getAdjustmentTabPage().chooseOption("Reason");
                pages.getAdjustmentTabPage().writeComment("Performing Credit Type Adjustment through automation");
                pages.getAdjustmentTabPage().enterMainAmount("0.00001");
                pages.getAdjustmentTabPage().clickSubmitButton();
                assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().getPopUpTitle().contains("CREDIT"), true, "Pop up title as per adjustment type", "Pop up title does not as per adjustment type"));
                pages.getAdjustmentTabPage().clickYesButton();
                AdjustmentHistory adjustmentHistoryAPI = api.getAdjustMentHistory(hbbCustomerNumber);
                final int statusCode = adjustmentHistoryAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false));
                AdjustmentResult result = adjustmentHistoryAPI.getResult().get(0);
                assertCheck.append(actions.matchUiAndAPIResponse(result.getReason(), adjustmentReason, "Adjustment reason same as selected while performing adjustment", "Adjustment reason as not selected while performing adjustment"));
                assertCheck.append(actions.assertEqualBoolean(result.getAdjustmentType().contains("Credit"), true, "Adjustment type same as selected while performing adjustment", "Adjustment type not same as selected while performing adjustment"));
                assertCheck.append(actions.assertEqualBoolean(result.getAccountType().contains("Main"), true, "Adjustment account type same as selected while performing adjustment", "Adjustment account type not same as selected while performing adjustment"));
                assertCheck.append(actions.matchUiAndAPIResponse(result.getAmount(), "0.00001", "Adjustment Amount same as selected while performing adjustment", "Adjustment Amount as not selected while performing adjustment"));
                assertCheck.append(actions.matchUiAndAPIResponse(result.getComment(), "Performing Credit Type Adjustment through automation", "Adjustment Comment same as selected while performing adjustment", "Adjustment Comment as not selected while performing adjustment"));
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAdjustmentCreditType" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6, dependsOnMethods = {"openCustomerInteraction"}, groups = {"RegressionTest", "ProdTest", "SanityTest"})
    public void validateAdjustmentDebitType() {
        try {
            assertCheck.append(actions.assertEqualBoolean(!pages.getAdjustmentTabPage().isAccessDeniedMsg(), true, "Debit type adjustment permitted by the agent as expected", "Debit type adjustment does not permitted as per user permission", true));
            selUtils.addTestcaseDescription("Validate user able to open adjustment tab through home action,Validate agent able to choose fields reason and type and enter comment,Validate admin able to put comment,Validate agent able to click submit button and agent must receive proper success message", "description");
            Boolean visible = pages.getCustomerProfilePage().checkAdjustmentAction();
            if (visible) {
                pages.getAdjustmentTabPage().OpenAdjustmentReason();
                pages.getAdjustmentTabPage().chooseOption("Reason");
                pages.getAdjustmentTabPage().writeComment("Performing debit Type Adjustment through automation");
                pages.getAdjustmentTabPage().enterMainAmount("0.00001");
                pages.getAdjustmentTabPage().clickSubmitButton();
                assertCheck.append(actions.assertEqualBoolean(pages.getAdjustmentTabPage().getPopUpTitle().contains("DEBIT"), true, "Pop up title as per adjustment type", "Pop up title does not as per adjustment type"));
                pages.getAdjustmentTabPage().clickYesButton();
                AdjustmentHistory adjustmentHistoryAPI = api.getAdjustMentHistory(hbbCustomerNumber);
                final int statusCode = adjustmentHistoryAPI.getStatusCode();
                assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Adjustment History API success and status code is :" + statusCode, "Adjustment History API got failed and status code is :" + statusCode, false));
                AdjustmentResult result = adjustmentHistoryAPI.getResult().get(0);
                assertCheck.append(actions.matchUiAndAPIResponse(result.getReason(), adjustmentReason, "Adjustment reason same as selected while performing adjustment", "Adjustment reason as not selected while performing adjustment"));
                assertCheck.append(actions.assertEqualBoolean(result.getAdjustmentType().contains("Debit"), true, "Adjustment type same as selected while performing adjustment", "Adjustment type not same as selected while performing adjustment"));
                assertCheck.append(actions.assertEqualBoolean(result.getAccountType().contains("Main"), true, "Adjustment account type same as selected while performing adjustment", "Adjustment account type not same as selected while performing adjustment"));
                assertCheck.append(actions.matchUiAndAPIResponse(result.getAmount(), "0.00001", "Adjustment Amount same as selected while performing adjustment", "Adjustment Amount as not selected while performing adjustment"));
                assertCheck.append(actions.matchUiAndAPIResponse(result.getComment(), "Performing debit Type Adjustment through automation", "Adjustment Comment same as selected while performing adjustment", "Adjustment Comment as not selected while performing adjustment"));
            }
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAdjustmentDebitType" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
