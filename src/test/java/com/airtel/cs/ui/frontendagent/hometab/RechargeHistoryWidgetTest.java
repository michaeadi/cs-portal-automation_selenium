package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.RechargeHistoryWidget;
import com.airtel.cs.pojo.response.RechargeHistoryPOJO;
import lombok.extern.java.Log;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log
public class RechargeHistoryWidgetTest extends Driver {

    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, description = "Validate Customer Interaction Page")
    public void openCustomerInteractionAPI() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
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

    @DataProviders.Table(name = "Recharge History")
    @Test(priority = 2, description = "Validating Recharge History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void rechargeHistoryWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Recharge History Widget of User :" + customerNumber, "description");
            final RechargeHistoryWidget rechargeHistoryWidget = pages.getRechargeHistoryWidget();
            assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryWidgetIsVisible(), true, "Recharge History Widget is visible", "Recharge History Widget is not visible"));
            assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryDatePickerVisible(), true, "Recharge History Widget's Date Picker is visible", "Recharge History Widget's Date Picker is not visible"));
            RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
            if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
                assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryErrorVisible(), true, "com.airtel.cs.API and widget both are Giving error", "com.airtel.cs.API is Giving error But Widget is not showing error Message on com.airtel.cs.API is"));
                commonLib.fail("com.airtel.cs.API is unable to give Recharge History ", true);
            } else {
                int size = rechargeHistoryWidget.getNumberOfRows();
                if (rechargeHistoryAPI.getResult().size() == 0 || rechargeHistoryAPI.getResult() == null) {
                    commonLib.warning("Unable to get Last Recharge Details from com.airtel.cs.API");
                    assertCheck.append(actions.assertEqual_boolean(rechargeHistoryWidget.isRechargeHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.gettingRechargeHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
                } else {
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(1).toLowerCase().trim() + " " + rechargeHistoryWidget.getSubHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(4).toLowerCase().trim() + rechargeHistoryWidget.getSubHeaders(4).toLowerCase().trim().replace("|", ""), data.getRow4().toLowerCase().replace("|", "").trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                    assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                    for (int i = 0; i < size; i++) {
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryCharges(i + 1), rechargeHistoryAPI.getResult().get(i).getCharges(), "Recharge History Charge is As received in com.airtel.cs.API for row number " + i, "Recharge History Charge is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), UtilsMethods.getDateFromString(rechargeHistoryAPI.getResult().get(i).getDateTime(), config.getProperty("UIRechargeHistoryTimeFormat"), config.getProperty("APIRechargeHistoryTimeFormat")), "Recharge History Date Time is As received in com.airtel.cs.API for row number " + i, "Recharge History Date Time is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBundleName(i + 1), rechargeHistoryAPI.getResult().get(i).getBundleName(), "Recharge History Bundle Name is As received in com.airtel.cs.API for row number " + i, "Recharge History Bundle Name is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryBenefits(i + 1).replace("-", "null"), rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getVOICE() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() + " | " + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getSMS(), "Recharge History Benefits is As received in com.airtel.cs.API for row number " + i, "Recharge History Benefits is not As received in com.airtel.cs.API for row number " + i));
                        assertCheck.append(actions.assertEqual_stringType(rechargeHistoryWidget.getRechargeHistoryStatus(i + 1), rechargeHistoryAPI.getResult().get(i).getStatus(), "Recharge History Status is As received in com.airtel.cs.API for row number " + i, "Recharge History Status is not As received in com.airtel.cs.API for row number " + i));
                        if (i != 0) {
                            assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1), rechargeHistoryWidget.getRechargeHistoryDateTime(i), "dd-MMM-yyy HH:mm"), true, rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "displayed before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i), rechargeHistoryWidget.getRechargeHistoryDateTime(i + 1) + "should not display before " + rechargeHistoryWidget.getRechargeHistoryDateTime(i)));
                        }
                    }

                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }
}
