package com.airtel.cs.ui.frontendagent.demographicwidget;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.utils.UtilsMethods;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.actionconfig.ActionConfigResult;
import com.airtel.cs.model.cs.response.actionconfig.Condition;
import com.airtel.cs.model.cs.response.agents.RoleDetails;
import com.airtel.cs.model.cs.response.amprofile.AMProfile;
import com.airtel.cs.model.cs.response.filedmasking.FieldMaskConfigs;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;


public class AmProfileTest extends Driver {

    RequestSource api = new RequestSource();
    String customerNumber;
    AMProfile amProfileAPI;
    public static final String AIRTEL_MONEY_PROFILE = constants.getValue(ApplicationConstants.AIRTEL_MONEY_PROFILE);

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @BeforeMethod(groups = {"ProdTest", "SanityTest", "RegressionTest"})
    public void checkAirtelMoneyFlag() {
        if (!StringUtils.equals(AIRTEL_MONEY_PROFILE, "true")) {
            commonLib.skip("Skipping because Run Airtel Money widget Test Case Flag Value is - " + AIRTEL_MONEY_PROFILE);
            throw new SkipException("Skipping because this functionality does not applicable for current Opco");
        }
    }


    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            final String customerNumber = constants.getValue(ApplicationConstants.AM_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
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
    public void validateAmProfile() {
        try {
            selUtils.addTestcaseDescription("Verify Airtel Money Profile data after unlocking the same", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getMiddleAuuidAMP(), loginAUUID, "Auuid is visible at the middle of the Airtel Money Profile widget and is correct", "Auuid is NOT visible at the middle of the Airtel Money Profile widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getFooterAuuidAMP(), loginAUUID, "Auuid is visible at the footer of the Airtel Money Profile widget and is correct", "Auuid is NOT visible at the footer of the Airtel Money Profile widget"));
            if (pages.getDemoGraphicPage().isAirtelMoneyProfileLocked()) {
                try {
                    assertCheck = pages.getAmProfile().amAuthenticate();
                } catch (Exception e) {
                    pages.getAuthTabPage().clickCloseBtn();
                    commonLib.fail("Not able to unlock Airtel Money Profile", true);
                }
            }
            amProfileAPI = api.amServiceProfileAPITest(customerNumber);
            final int amProfileAPIStatusCode = amProfileAPI.getStatusCode();
            if (amProfileAPIStatusCode == 200) {
                assertCheck.append(actions.assertEqualIntType(amProfileAPIStatusCode, 200, "AM Profile API Status Code Matched and is :" + amProfileAPIStatusCode, "AM Profile API Status Code NOT Matched and is :" + amProfileAPIStatusCode, false));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getAccountStatus().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getAccountStatus()), "Customer's Airtel Money Status is as Expected", "Customer's Airtel Money Status is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getRegistrationStatus().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getRegStatus()), "Customer's Airtel Money Registration Status as Expected", "Customer's Airtel Money Registration Status not same not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getWalletType().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getWalletType()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getGrade().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getGrade()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getBarredReason().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getBarDetails().getBarReason()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getBarredOn().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getBarDetails().getBarredOn()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                String serviceStatus = pages.getDemoGraphicPage().getServiceStatus().toLowerCase().trim();
                assertCheck.append(actions.assertEqualStringType(serviceStatus, pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getServiceStatus()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                if (!(serviceStatus.equalsIgnoreCase("Active") || serviceStatus.equalsIgnoreCase("-"))) {
                    pages.getAmProfile().hoverServiceStatusMessageIcon();
                    assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getRemarks().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getBarDetails().getRemarks()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                    assertCheck.append(actions.assertEqualStringType(pages.getAmProfile().getBarredBy().toLowerCase().trim(), pages.getDemoGraphicPage().getKeyValueAPI(amProfileAPI.getResult().getBarDetails().getBarredBy()), "Customer's Airtel Money Service Status is as Expected", "Customer's Airtel Money Service Status is not as Expected"));
                } else
                    commonLib.warning("Service Status is :" + serviceStatus + " so unable to display Barred By and Remarks");
            } else
                commonLib.warning("Am Profile status code is : " + amProfileAPIStatusCode + " so unable to display data");
        } catch (Exception e) {
            commonLib.fail("Exception in Method - validateAmProfile" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void checkAmMasking() {
        try {
            selUtils.addTestcaseDescription("Verify Airtel Money Profile's maksing", "description");
            String airtelMoneyString = pages.getDemoGraphicPage().getWalletBalance().replaceAll("[^0-9]", "").trim();
            int airtelMoney = StringUtils.isEmpty(airtelMoneyString) ? 0 : Integer.parseInt(airtelMoneyString);
            ActionConfigResult actionConfigResult = api.getActionConfig("resetPin");
            List<String> actionConfigRoles = actionConfigResult.getRoles();
            List<RoleDetails> agentRoles = UtilsMethods.getAgentDetail().getUserDetails().getUserDetails().getRole();
            boolean hasRole = ObjectUtils.isNotEmpty(actionConfigRoles) && agentRoles.stream().anyMatch(roleName -> actionConfigRoles.contains(roleName.getRoleName()));
            String operator;
            if (ObjectUtils.isNotEmpty(actionConfigResult.getConditions())) {
                Condition condition = actionConfigResult.getConditions().get(0);
                operator = condition.getOperator();
                Integer thresholdValue = Integer.parseInt(condition.getThresholdValue());
                if (hasRole && (">=".equals(operator) && airtelMoney >= thresholdValue || "<".equals(operator) && airtelMoney < thresholdValue || "=".equals(operator) && airtelMoney == thresholdValue || "<=".equals(operator) && airtelMoney <= thresholdValue || ">".equals(operator) && airtelMoney > thresholdValue)) {
                    assertCheck.append(actions.assertEqualBoolean(pages.getDemoGraphicPage().isResetPinIconDisable(), true, "Reset PIN Icon is disable as mentioned in CS API Response", "Reset PIN Icon is not disable as mentioned in CS API Response"));
                }
            } else
                assertCheck.append(actions.assertEqualBoolean(pages.getDemoGraphicPage().isResetPinIconDisable(), false, "Reset PIN Icon is enable as mentioned in CS API Response", "Reset PIN Icon is not enable as mentioned in CS API Response"));
            FieldMaskConfigs amBalanceFieldMaskConfigs = api.getFieldMaskConfigs("amBalance");
            operator = amBalanceFieldMaskConfigs.getOperator();
            int amThresholdValue = StringUtils.isEmpty(amBalanceFieldMaskConfigs.getThresholdValue()) ? 0 : Integer.parseInt(amBalanceFieldMaskConfigs.getThresholdValue());
            hasRole = ObjectUtils.isNotEmpty(amBalanceFieldMaskConfigs.getRoles()) && agentRoles.stream().anyMatch(roleName -> amBalanceFieldMaskConfigs.getRoles().contains(roleName.getRoleName()));
            if (hasRole && ((">=".equals(operator) && airtelMoney >= amThresholdValue) || ("<".equals(operator)
                    && airtelMoney < amThresholdValue) || ("=".equals(operator) && airtelMoney == amThresholdValue) || ("<=".equals(operator)
                    && airtelMoney <= amThresholdValue) || (">".equals(operator) && airtelMoney > amThresholdValue))) {
                assertCheck.append(actions.assertEqualBoolean(airtelMoneyString.length() == amBalanceFieldMaskConfigs.getDigitsVisible(), true, "Airtel Money masking is correct as per user role", "Airtel Money masking is not correct as per user role"));
            } else {
                assertCheck.append(actions.assertEqualBoolean(airtelMoneyString.contains("*"), false, "Airtel Money is not masked as per user role", "Airtel Money should not be masked as per user role"));
                assertCheck.append(actions.assertEqualStringType(pages.getDemoGraphicPage().getWalletBalance(), amProfileAPI.getResult().getWallets().get(0).getCurrency().toUpperCase() + " " + amProfileAPI.getResult().getWallets().get(0).getBalance(), "Customer's Airtel Wallet Balance & Currency code as Expected", "Customer's Airtel Wallet Balance & Currency code not same not as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - checkAmMasking " + e, true);
        }
    }
}




