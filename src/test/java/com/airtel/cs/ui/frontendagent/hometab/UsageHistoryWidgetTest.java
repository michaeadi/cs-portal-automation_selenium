package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.agents.RoleDetails;
import com.airtel.cs.model.cs.response.filedmasking.FieldMaskConfigs;
import com.airtel.cs.model.cs.response.usagehistory.UsageHistory;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidget;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

@Log4j2
public class UsageHistoryWidgetTest extends Driver {

    public static final String RUN_USAGE_WIDGET_TEST_CASE = constants.getValue(ApplicationConstants.RUN_USAGE_WIDGET_TESTCASE);
    private static String customerNumber = null;
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkUsageHistoryFlag() {
        if (!StringUtils.equals(RUN_USAGE_WIDGET_TEST_CASE, "true")) {
            commonLib.skip("Skipping because Run Usage widget Test Case Flag Value is - " + RUN_USAGE_WIDGET_TEST_CASE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "openCustomerInteraction")
    public void usageHistoryWidgetHeaderTest() {
        try {
            selUtils.addTestcaseDescription("Validate is Usage History Widget Visible?,Validate footer and middle auuid,Validate Header Text", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getUsageHistoryWidget().isUsageHistoryWidgetIsVisible(), true, "Usage History Widget is Visible", "Usage History Widget is NOT Visible"));
            assertCheck.append(actions.assertEqualStringType(pages.getUsageHistoryWidget().getFooterAuuidUHW(), loginAUUID, "Auuid shown at the footer of the Your Usage History widget and is correct", "Auuid NOT shown at the footer of Your Usage History widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getUsageHistoryWidget().getMiddleAuuidUHW(), loginAUUID, "Auuid shown at the middle of the Your Usage History widget and is correct", "Auuid NOT shown at the middle of Your Usage History widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getUsageHistoryWidget().getUsageHistoryHeaderText(), "USAGE HISTORY", "Usage History Widget Header Text Matched", "Usage History Widget Header Text NOT Matched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - usageHistoryWidgetHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @DataProviders.Table(name = "Usage History")
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void usageHistoryWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Usage History Widget of User :" + customerNumber, "description");
            final UsageHistoryWidget usageHistoryWidget = pages.getUsageHistoryWidget();
            assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.isUsageHistoryDatePickerVisible(), true, "Usage History Widget's Date Picker is visible", "Usage History Widget's Date Picker is not visible"));
            UsageHistory usageHistoryAPI = api.usageHistoryTest(customerNumber);
            int size = usageHistoryWidget.getNumberOfRows();
            if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else {
                for (int i = 0; i < data.getHeaderName().size(); i++) {
                    assertCheck.append(actions.matchUiAndAPIResponse(usageHistoryWidget.getHeaders(i + 1), data.getHeaderName().get(i), "Header Name for Row " + (i + 1) + " is as expected", "Header Name for Row " + (i + 1) + " is not as expected"));
                }
                FieldMaskConfigs chargesfieldMaskConfigs = api.getFieldMaskConfigs("usageCdrCharges");
                FieldMaskConfigs startBalancefieldMaskConfigs = api.getFieldMaskConfigs("usageCdrStartBalance");
                FieldMaskConfigs endBalancefieldMaskConfigs = api.getFieldMaskConfigs("usageCdrEndBalance");
                FieldMaskConfigs typeFieldMaskingConfigs = api.getFieldMaskConfigs("usageCdrType");
                List<RoleDetails> roleDetails = UtilsMethods.getAgentDetail().getUserDetails().getUserDetails().getRole();

                for (int i = 0; i < size; i++) {
                    int row = i + 1;
                    if (CollectionUtils.isNotEmpty(typeFieldMaskingConfigs.getRoles()) && roleDetails.stream()
                            .anyMatch(roleName -> typeFieldMaskingConfigs.getRoles().contains(roleName.getRoleName()))) {
                        assertCheck.append(actions.assertEqualIntType(usageHistoryWidget.getHeaderValue(row, 1).replace("*", "").length(), typeFieldMaskingConfigs.getDigitsVisible(), "Usage History Type masking is correct as per user role for row " + row, "Usage History Type masking is not correct as per user role for row " + row));
                    } else {
                        assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.getHeaderValue(row, 1).contains("*"), false, "Usage History Type is not masked as per user role for row " + row, "Usage History Type should not be masked as per user role for row " + row));
                        assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.getHeaderValue(row, 1), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is As received in CS API for row number " + row, "Usage History Type is not As received in CS API for row number " + row));
                    }
                    if (CollectionUtils.isNotEmpty(chargesfieldMaskConfigs.getRoles()) && roleDetails.stream()
                            .anyMatch(roleName -> chargesfieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
                        assertCheck.append(actions.assertEqualIntType(usageHistoryWidget.getHeaderValue(row, 2).replaceAll("[^0-9]", "").trim().length(), chargesfieldMaskConfigs.getDigitsVisible(), "Usage History Charge masking is correct as per user role for row " + row, "Usage History Charge masking is not correct as per user role for row " + row));
                    } else {
                        assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.getHeaderValue(row, 2).contains("*"), false, "Usage History Charge is not masked as per user role for row " + row, "Usage History Charge should not be masked as per user role for row " + row));
                        assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.getHeaderValue(row, 2).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is As received in CS API for row number " + row, "Usage History Charge is not As received in CS API for row number " + row));
                    }
                    if (CollectionUtils.isNotEmpty(startBalancefieldMaskConfigs.getRoles()) && roleDetails.stream()
                            .anyMatch(roleName -> startBalancefieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
                        assertCheck.append(actions.assertEqualIntType(usageHistoryWidget.getHeaderValue(row, 4).replaceAll("[^0-9]", "").trim().length(), startBalancefieldMaskConfigs.getDigitsVisible(), "Usage History Start Balance masking is correct as per user role for row " + row, "Usage History Start Balance masking is not correct as per user role for row " + row));
                    } else {
                        assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.getHeaderValue(row, 4).contains("*"), false, "Usage History Start Balance is not masked as per user role for row " + row, "Usage History Start Balance should not be masked as per user role for row " + row));
                        assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.getHeaderValue(row, 4), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is As received in CS API for row number " + row, "Usage History Start Balance  is not As received in CS API for row number " + row));
                    }
                    if (CollectionUtils.isNotEmpty(endBalancefieldMaskConfigs.getRoles()) && roleDetails.stream()
                            .anyMatch(roleName -> endBalancefieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
                        assertCheck.append(actions.assertEqualIntType(usageHistoryWidget.getHeaderValue(row, 5).replaceAll("[^0-9]", "").trim().length(), endBalancefieldMaskConfigs.getDigitsVisible(), "Usage History End Balance masking is correct as per user role for row " + row, "Usage History End Balance masking is not correct as per user role for row " + row));
                    } else {
                        assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.getHeaderValue(row, 5).contains("*"), false, "Usage History End Balance is not masked as per user role for row " + row, "Usage History End Balance should not be masked as per user role for row " + row));
                        assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.getHeaderValue(row, 5), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is As received in CS API for row number " + row, "Usage History End Balance is not As received in CS API for row number " + row));
                    }
                    assertCheck.append(actions.assertEqualStringType(usageHistoryWidget.getHeaderValue(row, 3), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is As received in CS API for row number " + row, "Usage History Date Time is not As received in CS API for row number " + row));
                    if (i > 1) {
                        assertCheck.append(actions.assertEqualBoolean(UtilsMethods.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(row).replace("\n", " "), usageHistoryWidget.getHistoryDateTime(row - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), true, usageHistoryWidget.getHistoryDateTime(row) + " displayed before " + usageHistoryWidget.getHistoryDateTime(row - 1), usageHistoryWidget.getHistoryDateTime(row - 1) + " should not display before " + usageHistoryWidget.getHistoryDateTime(row)));
                    }
                }
            }
            if (usageHistoryAPI.getStatusCode() != 200) {
                assertCheck.append(actions.assertEqualBoolean(usageHistoryWidget.isUsageHistoryErrorVisible(), true, "API and widget both are giving error message", "API is Giving error But Widget is not showing error Message on CS API is "));
                commonLib.fail("CS API is unable to give Usage History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - usageHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }
}
