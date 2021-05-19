package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.response.AccountsBalancePOJO;
import com.airtel.cs.pojo.response.RechargeHistoryPOJO;
import com.airtel.cs.pojo.response.UsageHistoryPOJO;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DataUnitConversionTest extends Driver {
    static String customerNumber;
    RequestSource api = new RequestSource();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login via API");
            throw new SkipException("Skipping tests because user NOT able to login via API");
        }
    }

    @DataProviders.User(userType = "API")
    @Test(priority = 0, dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void setCustomerNumber(TestDatabean data) {
        customerNumber = data.getCustomerNumber();
    }

    @Test(priority = 1, description = "Validating Usage History Widget MB to GB Conversion")
    public void usageHistoryWidgetTest() {
        selUtils.addTestcaseDescription("Validating Usage History Widget MB to GB Conversion,CSP-63391 Verify that in Usage History Widget if the data amount is coming MB from ESB com.airtel.cs.API then CS Portal should show the data amount after converting it to GB", "description");
        SoftAssert softAssert = new SoftAssert();
        String startBalanceUnit = null;
        String endBalanceUnit = null;
        Double startBalanceAmount = null;
        Double endBalanceAmount = null;

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryTest(customerNumber);
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.fail("API is unable to give Usage History ");
        } else if (usageHistoryAPI.getResult().size() == 0 || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
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
                            softAssert.fail("Start Balance and End Balance Unit is not same. Both must be same.");
                        } else if ((startBalanceUnit.equalsIgnoreCase("MB") && startBalanceAmount > 1024) || endBalanceAmount > 1024) {
                            softAssert.fail("MB to GB conversion does not done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        }
                    } else {
                        softAssert.fail("Start Balance is not empty but End Balance is Empty. Some Issue with data. Please verify");
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validating Usage History Menu Widget MB to GB Conversion")
    public void usageHistoryMenuWidgetTest() {
        selUtils.addTestcaseDescription("Validating Usage History Menu Widget MB to GB Conversion,CSP-63392 Verify that in Usage History Detailed Widget if the data amount is coming MB from ESB com.airtel.cs.API then CS Portal should show the data amount after converting it to GB", "description");
        SoftAssert softAssert = new SoftAssert();
        String startBalanceUnit = null;
        String endBalanceUnit = null;
        Double startBalanceAmount = null;
        Double endBalanceAmount = null;

        UsageHistoryPOJO usageHistoryAPI = api.usageHistoryMenuTest(customerNumber);
        if (usageHistoryAPI.getStatusCode() != 200) {
            softAssert.fail("API is unable to give Usage History ");
        } else if (usageHistoryAPI.getResult().isEmpty() || usageHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
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
                            softAssert.fail("Start Balance and End Balance Unit is not same. Both must be same.");
                        } else if ((startBalanceUnit.equalsIgnoreCase("MB") && startBalanceAmount > 1024) || endBalanceAmount > 1024) {
                            softAssert.fail("MB to GB conversion does not done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Start Balance" + usageHistoryAPI.getResult().get(i).getStartBalance() + " " + usageHistoryAPI.getResult().get(i).getEndBalance());
                        }
                    } else {
                        softAssert.fail("Start Balance is not empty but End Balance is Empty. Some Issue with data. Please verify");
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validating Recharge History Widget MB to GB Conversion")
    public void rechargeHistoryUnitConversionTest() {
        selUtils.addTestcaseDescription("Validating Recharge History Widget MB to GB Conversion,CSP-63393 Verify that in Recharge History Widget if the data amount is coming MB from ESB com.airtel.cs.API then CS Portal should show the data amount after converting it to GB", "description");
        SoftAssert softAssert = new SoftAssert();

        RechargeHistoryPOJO rechargeHistoryAPI = api.rechargeHistoryAPITest(customerNumber);
        if (rechargeHistoryAPI.getStatusCode() != 200 || rechargeHistoryAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.fail("API is unable to give Recharge History ");
        } else if (rechargeHistoryAPI.getResult().isEmpty() || rechargeHistoryAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
        } else {
            for (int i = 0; i < rechargeHistoryAPI.getResult().size(); i++) {
                if (rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA() != null && !rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().equalsIgnoreCase("0")) {
                    double dataBalanceAmount = Double.parseDouble(rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().split(" ")[0]);
                    String dataBalanceUnit = rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA().split(" ")[1];
                    if (dataBalanceUnit != null) {
                        if (dataBalanceUnit.equalsIgnoreCase("MB") && dataBalanceAmount > 1024) {
                            softAssert.fail("MB to GB conversion does not done Correctly. Balance" + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA());
                        } else {
                            commonLib.pass("MB to GB conversion done Correctly. Balance" + rechargeHistoryAPI.getResult().get(i).getRechargeBenefit().getDATA());
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validating DA Details History Widget MB to GB Conversion")
    public void daDetailsUnitConversionTest() {
        selUtils.addTestcaseDescription("Validating DA Details History Widget MB to GB Conversion,CSP-63396 Verify that in DA Details Widget if the data amount is coming MB from ESB com.airtel.cs.API then CS Portal should show the data amount after converting it to GB", "description");
        SoftAssert softAssert = new SoftAssert();

        AccountsBalancePOJO plansAPI = api.balanceAPITest(customerNumber);
        if (plansAPI.getStatusCode() != 200 || plansAPI.getStatus().equalsIgnoreCase("something went wrong")) {
            softAssert.fail("API is unable to give DA Details History ");
        } else if (plansAPI.getResult().isEmpty() || plansAPI.getResult() == null) {
            commonLib.warning("Unable to get Usage History Details from com.airtel.cs.API");
        } else {
            for (int i = 0; i < plansAPI.getResult().size(); i++) {
                if (plansAPI.getResult().get(i).getCurrentDaBalance() != null && !plansAPI.getResult().get(i).getCurrentDaBalance().equalsIgnoreCase("0")) {
                    double dataBalanceAmount = Double.parseDouble(plansAPI.getResult().get(i).getCurrentDaBalance().split(" ")[0]);
                    String dataBalanceUnit = plansAPI.getResult().get(i).getCurrentDaBalance().split(" ")[1];
                    if (dataBalanceUnit != null) {
                        if (dataBalanceUnit.equalsIgnoreCase("MB") && dataBalanceAmount > 1024) {
                            softAssert.fail("MB to GB conversion does not done Correctly. Balance" + plansAPI.getResult().get(i).getCurrentDaBalance());
                        } else if (dataBalanceUnit.equalsIgnoreCase("MB")) {
                            commonLib.pass("MB to GB conversion done Correctly. Balance" + plansAPI.getResult().get(i).getCurrentDaBalance());
                        }
                    }
                }
            }
        }
        softAssert.assertAll();
    }
}
