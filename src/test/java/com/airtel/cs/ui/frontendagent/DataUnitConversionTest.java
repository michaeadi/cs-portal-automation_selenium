package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.AccountsBalancePOJO;
import com.airtel.cs.pojo.response.RechargeHistoryPOJO;
import com.airtel.cs.pojo.response.UsageHistoryPOJO;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DataUnitConversionTest extends Driver {
    static String customerNumber;
    RequestSource api = new RequestSource();
    public BaseActions actions = new BaseActions();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
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

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void usageHistoryWidgetTest() {
        selUtils.addTestcaseDescription("Validating Usage History Widget MB to GB Conversion,CSP-63391 Verify that in Usage History Widget if the data amount is coming MB from ESB CS API then CS Portal should show the data amount after converting it to GB", "description");
        String startBalanceUnit = null;
        String endBalanceUnit = null;
        Double startBalanceAmount = null;
        Double endBalanceAmount = null;

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(constants.getValue(ApplicationConstants.USAGE_HISTORY_MSISDN));
        if (usageHistoryAPI.getStatusCode() != 200) {
            commonLib.fail("API is unable to give Usage History", false);
        } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from CS API");
        } else {
            for (int i = 0; i < usageHistoryAPI.getResult().size(); i++) {
                if (usageHistoryAPI.getResult().get(i).getStartBalance().equalsIgnoreCase("-")) {
                    usageHistoryAPI.getResult().get(i).setStartBalance(null);
                } else {
                    startBalanceUnit = usageHistoryAPI.getResult().get(i).getStartBalance().split(" ")[1];
                    startBalanceAmount = Double.parseDouble(usageHistoryAPI.getResult().get(i).getStartBalance().split(" ")[0]);
                }

                if (usageHistoryAPI.getResult().get(i).getEndBalance().equalsIgnoreCase("-")) {
                    usageHistoryAPI.getResult().get(i).setEndBalance(null);
                } else {
                    endBalanceUnit = usageHistoryAPI.getResult().get(i).getEndBalance().split(" ")[1];
                    endBalanceAmount = Double.parseDouble(usageHistoryAPI.getResult().get(i).getEndBalance().split(" ")[0]);
                }
                if (startBalanceUnit != null) {
                    if (endBalanceUnit != null) {
                        if (!startBalanceUnit.equalsIgnoreCase(endBalanceUnit)) {
                            commonLib.fail("Start Balance and End Balance Unit is not same. Both must be same.", true);
                        } else if ((startBalanceUnit.equalsIgnoreCase("MB") && startBalanceAmount > 1024) || endBalanceAmount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance(), true);
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        }
                    } else {
                        commonLib.fail("Start Balance is not empty but End Balance is Empty. Some Issue with data. Please verify", true);
                    }
                }
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void usageHistoryMenuWidgetTest() {
        selUtils.addTestcaseDescription("Validating Usage History Menu Widget MB to GB Conversion,CSP-63392 Verify that in Usage History Detailed Widget if the data amount is coming MB from ESB CS API then CS Portal should show the data amount after converting it to GB", "description");
        String startBalanceUnit = null;
        String endBalanceUnit = null;
        Double startBalanceAmount = null;
        Double endBalanceAmount = null;
        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryMenuTest(constants.getValue(ApplicationConstants.USAGE_HISTORY_MSISDN));
        if (usageHistoryAPI.getStatusCode() != 200) {
            commonLib.fail("API is unable to give Usage History", false);
        } else if (usageHistoryAPI.getResult().isEmpty() || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from CS API");
        } else {
            for (int i = 0; i < usageHistoryAPI.getResult().size(); i++) {
                if (usageHistoryAPI.getResult().get(i).getStartBalance().equalsIgnoreCase("-")) {
                    usageHistoryAPI.getResult().get(i).setStartBalance(null);
                } else {
                    startBalanceUnit = usageHistoryAPI.getResult().get(i).getStartBalance().split(" ")[1];
                    startBalanceAmount = Double.parseDouble(usageHistoryAPI.getResult().get(i).getStartBalance().split(" ")[0]);
                }

                if (usageHistoryAPI.getResult().get(i).getEndBalance().equalsIgnoreCase("-")) {
                    usageHistoryAPI.getResult().get(i).setEndBalance(null);
                } else {
                    endBalanceUnit = usageHistoryAPI.getResult().get(i).getEndBalance().split(" ")[1];
                    endBalanceAmount = Double.parseDouble(usageHistoryAPI.getResult().get(i).getEndBalance().split(" ")[0]);
                }
                if (startBalanceUnit != null) {
                    if (endBalanceUnit != null) {
                        if (!startBalanceUnit.equalsIgnoreCase(endBalanceUnit)) {
                            commonLib.fail("Start Balance and End Balance Unit is not same. Both must be same.", true);
                        } else if ((startBalanceUnit.equalsIgnoreCase("MB") && startBalanceAmount > 1024) || endBalanceAmount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance(), true);
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        }
                    } else {
                        commonLib.fail("Start Balance is not empty but End Balance is Empty. Some Issue with data. Please verify", true);
                    }
                }
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void rechargeHistoryUnitConversionTest() {
        selUtils.addTestcaseDescription("Validating Recharge History Widget MB to GB Conversion,CSP-63393 Verify that in Recharge History Widget if the data amount is coming MB from ESB com.airtel.cs.API then CS Portal should show the data amount after converting it to GB", "description");
        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(constants.getValue(ApplicationConstants.RECHARGE_HISTORY_MSISDN));
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            commonLib.fail("API is unable to give Recharge History", false);
        } else if (rechargeHistoryAPI.getResult().isEmpty() || rechargeHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from CS API");
        } else {
            for (int i = 0; i < rechargeHistoryAPI.getResult().size(); i++) {
                if (rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() != null && !rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().equalsIgnoreCase("0")) {
                    double dataBalanceAmount = Double.parseDouble(rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().split(" ")[0]);
                    String dataBalanceUnit = rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().split(" ")[1];
                    if (dataBalanceUnit != null) {
                        if (dataBalanceUnit.equalsIgnoreCase("MB") && dataBalanceAmount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Balance" + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA(), true);
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Balance" + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA());
                        }
                    }
                }
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void daDetailsUnitConversionTest() {
        selUtils.addTestcaseDescription("Validating DA Details History Widget MB to GB Conversion,CSP-63396 Verify that in DA Details Widget if the data amount is coming MB from ESB CS API then CS Portal should show the data amount after converting it to GB", "description");
        AccountsBalancePOJO plansAPI = api.balanceAPITest(customerNumber);
        if (plansAPI.getStatusCode() != 200 || plansAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            commonLib.fail("API is unable to give DA Details History", false);
        } else if (plansAPI.getResult().isEmpty() || plansAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from CS API");
        } else {
            for (int i = 0; i < plansAPI.getResult().size(); i++) {
                if (plansAPI.getResult().get(i).getCurrentDaBalance() != null && !plansAPI.getResult().get(i).getCurrentDaBalance().equalsIgnoreCase("0")) {
                    double dataBalanceAmount = Double.parseDouble(plansAPI.getResult().get(i).getCurrentDaBalance().split(" ")[0]);
                    String dataBalanceUnit = plansAPI.getResult().get(i).getCurrentDaBalance().split(" ")[1];
                    if (dataBalanceUnit != null) {
                        if (dataBalanceUnit.equalsIgnoreCase("MB") && dataBalanceAmount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Balance" + plansAPI.getResult().get(i).getCurrentDaBalance(), true);
                        } else if (dataBalanceUnit.equalsIgnoreCase("MB")) {
                            commonLib.pass("MB to GB conversion done Correctly. Balance" + plansAPI.getResult().get(i).getCurrentDaBalance());
                        }
                    }
                }
            }
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
