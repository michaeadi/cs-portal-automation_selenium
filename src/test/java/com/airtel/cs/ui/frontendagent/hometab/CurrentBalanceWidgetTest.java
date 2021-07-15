package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.actions.BaseActions;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.CurrentBalanceWidget;
import com.airtel.cs.pojo.response.PlansPOJO;
import com.airtel.cs.pojo.response.PlansResultPOJO;
import lombok.extern.log4j.Log4j2;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Log4j2
public class CurrentBalanceWidgetTest extends Driver {

    private static String customerNumber = null;
    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
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
            customerNumber = constants.getValue(ApplicationConstants.CURRENT_BALANCE_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
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
    public void currentBalanceWidgetHeaderTest() {
        try {
            selUtils.addTestcaseDescription("Validate Current Balance Widget is visible, Validate Footer AUUID, Validate Middle AUUID, Validate Widget Header Text", "description");
            assertCheck.append(actions.assertEqual_boolean(pages.getCurrentBalanceWidgetPage().isCurrentBalanceWidgetVisible(), true, "Current Balance Widget is visible", "Current Balance Widget is not visible"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().getFooterAuuidYCP(), loginAUUID, "Auuid shown at the footer of the Your Current Plan widget and is correct", "Auuid NOT shown at the footer of Your Current Plan widget"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().getMiddleAuuidTYP(), loginAUUID, "Auuid shown at the middle of the Your Current Plan widget and is correct", "Auuid NOT shown at the middle of Your Current Plan widget"));
            assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().getCurrentPlanHeaderText(), "YOUR CURRENT PLAN", "Current Plan Widget Header Text Matched", "Current Plan Widget Header Text NOT Matched"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - currentBalanceWidgetHeaderTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void mainAccountBalanceTest() {
        try {
            selUtils.addTestcaseDescription("Validate Main Account Balance,Validate Current Balance Currency,Validate Last Recharge Amount,Validate Last Recharge Date and Time", "description");
            PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
            PlansResultPOJO result = plansAPI.getResult();
            final int statusCode = plansAPI.getStatusCode();
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Plan API Success and status code is :" + statusCode, "Plan API got failed and status code is :" + statusCode));
            if (result.getMainAccountBalance() != null) {
                final String mainAccountBalance = pages.getCurrentBalanceWidgetPage().gettingMainAccountBalance();
                assertCheck.append(actions.assertEqual_stringType(mainAccountBalance, result.getMainAccountBalance().getBalance(), "Current Balance is as Received in API", "Current Balance is not as Received from API and current balance on ui is : " + mainAccountBalance));
                assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().gettingCurrentBalanceCurrency(), result.getMainAccountBalance().getCurrency(), "Current Balance Currency is as Received in API", "Current Balance Currency is not as Received in API"));
            } else {
                commonLib.warning("Unable to get Main Balance from API");
            }
            if (result.getLastRecharge() != null) {
                try {
                    assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().gettingLastRechargeAmount(), result.getLastRecharge().getAmount(), "Last Recharge is as Received in API", "Last Recharge is not as Received in API"));
                } catch (NumberFormatException e) {
                    commonLib.fail("Last Recharge is not in expected format", true);
                    commonLib.fail("Last Recharge is not in expected format", true);
                }
                String Time = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), constants.getValue(CommonConstants.LAST_RECHARGE_TIME_PATTERN));
                String Date = UtilsMethods.getDateFromEpochInUTC(result.getLastRecharge().getRechargeOn(), constants.getValue(CommonConstants.LAST_RECHARGE_DATE_PATTERN));
                assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().getLastRechargeDateTime(), Date + " " + Time, "Last Recharge Date and Time is as Received in API", "Last Recharge Date and Time is not as Received in API"));
            } else {
                commonLib.warning("Unable to get Last Recharge Details from API");
                assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().gettingLastRechargeAmount().replace('-', ' ').trim(), "", "Last Recharge Amount is as expected", "Last Recharge Amount is not as expected"));
                assertCheck.append(actions.assertEqual_stringType(pages.getCurrentBalanceWidgetPage().getLastRechargeDateTime(), "- -", "Last Recharge Date & Time is as expected", "Last Recharge Date & Time is not as expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - mainAccountBalanceTest" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void voiceDataSmsTest() {
        try {
            selUtils.addTestcaseDescription("Validating Current Balance Transaction Widget of User :" + customerNumber, "description");
            final CurrentBalanceWidget currentBalanceWidgetPage = pages.getCurrentBalanceWidgetPage();
            PlansPOJO plansAPI = api.accountPlansTest(customerNumber);
            PlansResultPOJO result = plansAPI.getResult();
            if (result.getVoice() != null) {
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceExpiryDate(), UtilsMethods.getDateFromEpoch(result.getVoice().getExpireTime(), constants.getValue(CommonConstants.BALANCE_EXPIRY_PATTERN)), "Voice Expiry Date is as Received in API", "Voice Expiry Date is not as Received in API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getVoiceBalance().replace("-", "null"), result.getVoice().getBalance(), "Voice Balance is as Received in API", "Voice Balance is not as Received in API"));
            }
            if (result.getData() != null) {
                try {
                    double amount = Double.parseDouble(result.getData().getBalance().split(" ")[0]);
                    if (amount > 0) {
                        String unit = result.getData().getBalance().split(" ")[1];
                        if (unit.equalsIgnoreCase("MB") && amount > 1024) {
                            commonLib.fail("MB to GB conversion does not done Correctly. Data Balance" + currentBalanceWidgetPage.getDataBalance(), true);
                        } else {
                            commonLib.pass("MB to GB Conversion Verified. Balance " + currentBalanceWidgetPage.getDataBalance());
                        }
                    }
                } catch (NumberFormatException ns) {
                    commonLib.info("Not able to fetch amount" + ns.fillInStackTrace());
                }
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataBalance().replace("-", "null"), result.getData().getBalance(), "Data Balance is as Received in API", "Data Balance is not as Received in API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getDataExpiryDate(), UtilsMethods.getDateFromEpoch(result.getData().getExpireTime(), constants.getValue(CommonConstants.BALANCE_EXPIRY_PATTERN)), "Data Expiry Date is as Received in API", "Data Expiry Date is not as Received in API"));
            }
            if (result.getSms() != null) {
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsExpiryDate(), UtilsMethods.getDateFromEpoch(result.getSms().getExpireTime(), constants.getValue(CommonConstants.BALANCE_EXPIRY_PATTERN)), "SMS Expiry Date is as Received in API", "SMS Expiry Date is not as Received in API"));
                assertCheck.append(actions.assertEqual_stringType(currentBalanceWidgetPage.getSmsBalance().replace("-", "null"), result.getSms().getBalance(), "SMS Balance is as Received in API", "SMS Balance is not as Received in API"));
            }
            if (plansAPI.getStatusCode() != 200) {
                commonLib.fail("API unable to get Last recharge and MAIN Balance ", true);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - yourCurrentBalanceWidgetTest" + e.fillInStackTrace(), true);
        }
    }
}
