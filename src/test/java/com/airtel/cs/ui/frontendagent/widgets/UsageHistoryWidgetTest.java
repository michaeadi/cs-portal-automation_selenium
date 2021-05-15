package com.airtel.cs.ui.frontendagent.widgets;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidget;
import com.airtel.cs.pojo.response.UsageHistoryPOJO;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

@Log4j2
public class UsageHistoryWidgetTest extends Driver {

    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

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

    @DataProviders.Table(name = "Usage History")
    @Test(priority = 2, description = "Validating Usage History Widget", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteractionAPI")
    public void usageHistoryWidgetTest(HeaderDataBean data) {
        try {
            selUtils.addTestcaseDescription("Validating Usage History Widget of User :" + customerNumber, "description");
            final UsageHistoryWidget usageHistoryWidget = pages.getUsageHistoryWidget();
            assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryDatePickerVisible(), true, "Usage History Widget's Date Picker is visible", "Usage History Widget's Date Picker is not visible"));
            UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
            int size = usageHistoryWidget.getNumberOfRows();
            if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
                commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
                assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryNoResultFoundVisible(), true, "Error Message is Visible", "Error Message is not Visible"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.gettingUsageHistoryNoResultFoundMessage(), "No Result found", "Error Message is as expected", "Error Message is not as expected"));
            } else {
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(1).toLowerCase().trim(), data.getRow1().toLowerCase().trim(), "Header Name for Row 1 is as expected", "Header Name for Row 1 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(2).toLowerCase().trim(), data.getRow2().toLowerCase().trim(), "Header Name for Row 2 is as expected", "Header Name for Row 2 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(3).toLowerCase().trim(), data.getRow3().toLowerCase().trim(), "Header Name for Row 3 is as expected", "Header Name for Row 3 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(4).toLowerCase().trim(), data.getRow4().toLowerCase().trim(), "Header Name for Row 4 is as expected", "Header Name for Row 4 is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHeaders(5).toLowerCase().trim(), data.getRow5().toLowerCase().trim(), "Header Name for Row 5 is as expected", "Header Name for Row 5 is not as expected"));
                for (int i = 0; i < size; i++) {
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryType(i), usageHistoryAPI.getResult().get(i).getType(), "Usage History Type is As received in com.airtel.cs.API for row number " + i, "Usage History Type is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryCharge(i).replaceAll("[^0-9]", "").trim(), usageHistoryAPI.getResult().get(i).getCharges().replaceAll("[^0-9]", ""), "Usage History Charge is As received in com.airtel.cs.API for row number " + i, "Usage History Charge is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryDateTime(i), usageHistoryAPI.getResult().get(i).getDateTime() + "\n" + usageHistoryAPI.getResult().get(i).getTime(), "Usage History Date Time is As received in com.airtel.cs.API for row number " + i, "Usage History Date Time is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryStartBalance(i), usageHistoryAPI.getResult().get(i).getStartBalance(), "Usage History Start Balance  is As received in com.airtel.cs.API for row number " + i, "Usage History Start Balance  is not As received in com.airtel.cs.API for row number " + i));
                    assertCheck.append(actions.assertEqual_stringType(usageHistoryWidget.getHistoryEndBalance(i), usageHistoryAPI.getResult().get(i).getEndBalance(), "Usage History End Balance is As received in com.airtel.cs.API for row number " + i, "Usage History End Balance is not As received in com.airtel.cs.API for row number " + i));
                    if (i != 0) {
                        assertCheck.append(actions.assertEqual_boolean(UtilsMethods.isSortOrderDisplay(usageHistoryWidget.getHistoryDateTime(i).replace("\n", " "), usageHistoryWidget.getHistoryDateTime(i - 1).replace("\n", " "), "EEE dd MMM yyy hh:mm:ss aa"), true, usageHistoryWidget.getHistoryDateTime(i - 1) + "displayed before " + usageHistoryWidget.getHistoryDateTime(i), usageHistoryWidget.getHistoryDateTime(i - 1) + "should not display before " + usageHistoryWidget.getHistoryDateTime(i)));
                    }
                }
            }
            if (usageHistoryAPI.getStatusCode() != 200) {
                assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.isUsageHistoryErrorVisible(), true, "API and widget both are giving error message", "API is Giving error But Widget is not showing error Message on com.airtel.cs.API is "));
                commonLib.fail("com.airtel.cs.API is unable to give Usage History ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - rechargeHistoryWidgetTest" + e.fillInStackTrace(), true);
        }
    }
}
