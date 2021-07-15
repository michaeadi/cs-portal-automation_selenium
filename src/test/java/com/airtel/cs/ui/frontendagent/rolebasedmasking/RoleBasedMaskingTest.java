package com.airtel.cs.ui.frontendagent.rolebasedmasking;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.AMTransactionsWidget;
import com.airtel.cs.pagerepository.pagemethods.DemoGraphic;
import com.airtel.cs.pagerepository.pagemethods.UsageHistoryWidget;
import com.airtel.cs.pojo.response.actionconfig.ActionConfigResponse;
import com.airtel.cs.pojo.response.actionconfig.ActionConfigResult;
import com.airtel.cs.pojo.response.actionconfig.Condition;
import com.airtel.cs.pojo.response.agents.RoleDetails;
import com.airtel.cs.pojo.response.airtelmoney.AirtelMoneyPOJO;
import com.airtel.cs.pojo.response.filedmasking.FieldMaskConfigs;
import io.restassured.http.Headers;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

public class RoleBasedMaskingTest extends Driver {

  String customerNumber = null;
  RequestSource api = new RequestSource();
  private List<RoleDetails> roleDetails = null;
  private Boolean hasRole = null;
  private List<ActionConfigResult> actionConfigResultList = null;


  @BeforeMethod(groups = {"SanityTest", "RegressionTest","ProdTest"})
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  @Test(priority = 1, groups = {"SanityTest", "RegressionTest","ProdTest"})
  public void openCustomerInteraction() {
    try {
      selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
      customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_MSISDN);
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

  @Test(priority = 2, groups = {"SanityTest", "RegressionTest","ProdTest"})
  public void checkReverseIconDisability(){
    try {
      selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Reverse Transaction is diabled/enabled as per CS API response or not", "description");
      ActionConfigResponse actionConfigResponse= api.getActionConfig(new Headers(map));
      Integer statusCode = 0;
      if(ObjectUtils.isNotEmpty(actionConfigResponse)){
        statusCode=actionConfigResponse.getStatusCode();
      }
      assertCheck.append(actions.assertEqual_intType(statusCode, 200, "Action Config API success and status code is :" + statusCode, "Action Config got failed and status code is :" + statusCode));
      if (statusCode == 200 && ObjectUtils.isNotEmpty(actionConfigResponse.getResult())) {
        actionConfigResultList = actionConfigResponse.getResult();
        Optional<ActionConfigResult> actionConfigResultop = actionConfigResultList.stream()
            .filter(result -> "reverseTransaction".equals(result.getActionKey())).findFirst();
        if (actionConfigResultop.isPresent()) {
          ActionConfigResult actionConfigResult = actionConfigResultop.get();
          List<String> roles = actionConfigResult.getRoles();
          roleDetails = UtilsMethods.getAgentDetail(new Headers(map)).getUserDetails().getUserDetails().getRole();
          hasRole = roleDetails.stream().anyMatch(roleName -> roles.contains(roleName.getRoleName()));
          final AMTransactionsWidget amTxnWidgetPage = pages.getAmTxnWidgetPage();
          AirtelMoneyPOJO amTransactionHistoryAPI = api.transactionHistoryAPITest(customerNumber);
            assertCheck.append(actions.assertEqual_intType(statusCode, 200, "AM Txn API success and status code is :" + statusCode, "AM Txn API got failed and status code is :" + statusCode));
          if (statusCode != 200) {
              assertCheck.append(actions.assertEqual_boolean(widgetMethods.isWidgetErrorIconDisplay(amTxnWidgetPage.getAMWidgetId()), true, "API and Widget both are showing error message", "API is Giving error But Widget is not showing error Message on API is " + amTransactionHistoryAPI.getMessage()));
          } else if (amTransactionHistoryAPI.getResult().getTotalCount() == null) {
              assertCheck.append(actions.assertEqual_boolean(widgetMethods.isNoResultFoundIconDisplay(amTxnWidgetPage.getAMWidgetId()), true, "'No Result Found' Icon displayed", "'No Result Found' Icon NOT displayed"));
          } else {
            int count = Math.min(amTransactionHistoryAPI.getResult().getTotalCount(), 5);
            Condition condition = actionConfigResult.getConditions().get(0);
            for (int i = 0; i < count; i++) {
              String amountString = amTxnWidgetPage.getHeaderValue(i + 1, 1).replaceAll("[^\\d.]", "");
              Integer amount = StringUtils.isEmpty(amountString) ? 0 : Integer.parseInt(amountString);
              if (hasRole) {
                if ("<=".equals(condition.getOperator()) && amount <= condition.getThresholdValue()) {
                    assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isReverseIconEnable(i + 1), false, "Reverse Transaction Icon is disabled as mentioned in CS API Response", "Reverse Transaction  Icon  does not disable as mentioned in CS API Response"));
                } else if (">".equals(condition.getOperator()) && amount > condition.getThresholdValue()) {
                    assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isReverseIconEnable(i + 1), false, "Reverse Transaction Icon is disabled as mentioned in CS API Response", "Reverse Transaction  Icon  does not disable as mentioned in CS API Response"));
                } else if ("=".equals(condition.getOperator()) && amount == condition.getThresholdValue()) {
                    assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isReverseIconEnable(i + 1), false, "Reverse Transaction Icon is disabled as mentioned in CS API Response", "Reverse Transaction  Icon  does not disable as mentioned in CS API Response"));
                } else {
                    assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isReverseIconEnable(i + 1), true, "Reverse Transaction Icon is enabled as mentioned in CS API Response", "Reverse Transaction  Icon  does not enable as mentioned in CS API Response"));
                }
              } else {
                  assertCheck.append(actions.assertEqual_boolean(amTxnWidgetPage.isReverseIconEnable(i + 1), true, "Reverse Transaction Icon is enabled as mentioned in CS API Response", "Reverse Transaction  Icon  does not enable as mentioned in CS API Response"));
              }
            }
          }
        }
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - checkReverseIconDisability" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);

  }

  @Test(priority = 3, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkResetPinDisabilityAndAmBalanceMasking() {
    try {
      selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Reset Pin is diabled/enabled as per CS API response or not", "description");
      Optional<ActionConfigResult> actionConfigResultop = actionConfigResultList.stream()
          .filter(result -> "resetPin".equals(result.getActionKey())).findFirst();
      String airtelMoneyString = pages.getDemoGraphicPage().getWalletBalance().replaceAll("[^0-9]", "").trim();
      int airtelMoney = StringUtils.isEmpty(airtelMoneyString) ? 0 : Integer.parseInt(airtelMoneyString);
      if (actionConfigResultop.isPresent()) {
        ActionConfigResult actionConfigResult = actionConfigResultop.get();
        pages.getDemoGraphicPage().clickAirtelStatusToUnlock();
        pages.getDemoGraphicPage().selectPolicyQuestion();
        pages.getAuthTabPage().clickAuthBtn();
        if (hasRole) {
          Condition condition = actionConfigResult.getConditions().get(0);
          if (">=".equals(condition.getOperator()) && airtelMoney >= condition.getThresholdValue()) {
            assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isResetPinIconDisable(), true, "Reset PIN Icon is disable as mentioned in CS API Response", "Reset PIN Icon is not disable as mentioned in CS API Response"));
          } else if ("<".equals(condition.getOperator()) && airtelMoney < condition.getThresholdValue()) {
            assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isResetPinIconDisable(), true, "Reset PIN Icon is disable as mentioned in CS API Response", "Reset PIN Icon is not disable as mentioned in CS API Response"));
          } else if ("=".equals(condition.getOperator()) && airtelMoney == condition.getThresholdValue()) {
            assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isResetPinIconDisable(), true, "Reset PIN Icon is disable as mentioned in CS API Response", "Reset PIN Icon is not disable as mentioned in CS API Response"));
          } else {
            assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isResetPinIconDisable(), false, "Reset PIN Icon is enable as mentioned in CS API Response", "Reset PIN Icon is not enable as mentioned in CS API Response"));
          }
        } else {
          assertCheck.append(actions.assertEqual_boolean(pages.getDemoGraphicPage().isResetPinIconDisable(), false, "Reset PIN Icon is enable as mentioned in CS API Response", "Reset PIN Icon is not enable as mentioned in CS API Response"));
        }
      }

      FieldMaskConfigs amBalancefieldMaskConfigs = api.getFieldMaskConfigs("amBalance");
      String operator = amBalancefieldMaskConfigs.getOperator();
      int amThresoldValue = StringUtils.isEmpty(amBalancefieldMaskConfigs.getThresholdValue())?0:Integer.parseInt(amBalancefieldMaskConfigs.getThresholdValue());
      if (roleDetails.stream().anyMatch(roleName -> amBalancefieldMaskConfigs.getRoles().contains(roleName.getRoleName())) && (
          (">=".equals(operator) && airtelMoney >= amThresoldValue) || ("<".equals(operator) && airtelMoney < amThresoldValue) || (
              "=".equals(operator) && airtelMoney == amThresoldValue))) {
          assertCheck.append(actions.assertEqual_boolean(airtelMoneyString.length() == amBalancefieldMaskConfigs.getDigitsVisible(), true, "Airtel Money masking is correct as per user role", "Airtel Money masking is not correct as per user role"));
      } else {
        assertCheck.append(actions.assertEqual_boolean(airtelMoneyString.contains("*"), false, "Airtel Money is not masked as per user role", "Airtel Money should not be masked as per user role"));
      }

    } catch (Exception e){
      commonLib.fail("Exception in Method - checkResetPinDisabilityAndAmBalanceMasking" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }

  @Test(priority = 4, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkNationalIdMasking() {
    try {
      DemoGraphic demographic = new DemoGraphic(driver);
      FieldMaskConfigs nationalIdfieldMaskConfigs = api.getFieldMaskConfigs("nationalId");
      pages.getDemoGraphicPage().hoverOnCustomerInfoIcon();
      if (roleDetails.stream().anyMatch(roleName -> nationalIdfieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
          assertCheck.append(actions.assertEqual_boolean(demographic.getIdNumber().replace("*", "").length()==nationalIdfieldMaskConfigs.getDigitsVisible(), true, "National Id masking is correct as per user role", "National Id masking is not correct as per user role"));
      } else {
          assertCheck.append(actions.assertEqual_boolean(demographic.getIdNumber().contains("*"), false, "National Id is not masked as per user role", "National Id should not be masked as per user role"));
      }

    } catch (Exception e){
      commonLib.fail("Exception in Method - checkNationalIdMasking" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }

  @Test(priority = 5, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkUsageHistoryMasking() {
    try {
      selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Usage History Columns are Masked/UnMasked as per CS API response or not", "description");
      FieldMaskConfigs chargesfieldMaskConfigs = api.getFieldMaskConfigs("usageCdrCharges");
      FieldMaskConfigs startBalancefieldMaskConfigs = api.getFieldMaskConfigs("usageCdrStartBalance");
      FieldMaskConfigs endBalancefieldMaskConfigs = api.getFieldMaskConfigs("usageCdrEndBalance");
      FieldMaskConfigs descriptionFieldMaskingConfigs = api.getFieldMaskConfigs("usageCdrDescription");
      FieldMaskConfigs typeFieldMaskingConfigs = api.getFieldMaskConfigs("usageCdrType");
      final UsageHistoryWidget usageHistoryWidget = pages.getUsageHistoryWidget();
      for (int i = 0; i < usageHistoryWidget.getNumberOfRows(); i++) {
        int row = i + 1;
        if (typeFieldMaskingConfigs.getRoles() != null && !typeFieldMaskingConfigs.getRoles().isEmpty() && roleDetails.stream()
            .anyMatch(roleName -> typeFieldMaskingConfigs.getRoles().contains(roleName.getRoleName()))) {
          assertCheck.append(actions.assertEqual_intType(usageHistoryWidget.getHeaderValue(row, 1).replace("*", "").length(), typeFieldMaskingConfigs.getDigitsVisible(), "Usage History Type masking is correct as per user role for row " + row, "Usage History Type masking is not correct as per user role for row " + row));
        } else {
          assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.getHeaderValue(row, 1).contains("*"), false , "Usage History Type is not masked as per user role for row "+ row, "Usage History Type should not be masked as per user role for row "+ row));
        }

        if (chargesfieldMaskConfigs.getRoles() != null && !chargesfieldMaskConfigs.getRoles().isEmpty() && roleDetails.stream()
            .anyMatch(roleName -> chargesfieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
          assertCheck.append(actions.assertEqual_intType(usageHistoryWidget.getHeaderValue(row, 2).replaceAll("[^0-9]", "").trim().length(), chargesfieldMaskConfigs.getDigitsVisible(), "Usage History Charge masking is correct as per user role for row " + row, "Usage History Charge masking is not correct as per user role for row " + row));
        } else {
          assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.getHeaderValue(row, 2).contains("*"), false , "Usage History Charge is not masked as per user role for row "+ row, "Usage History Charge should not be masked as per user role for row "+ row));
        }

        if (startBalancefieldMaskConfigs.getRoles() != null && !startBalancefieldMaskConfigs.getRoles().isEmpty() && roleDetails.stream()
            .anyMatch(roleName -> startBalancefieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
          assertCheck.append(actions.assertEqual_intType(usageHistoryWidget.getHeaderValue(row, 4).replaceAll("[^0-9]", "").trim().length(), startBalancefieldMaskConfigs.getDigitsVisible() , "Usage History Start Balance masking is correct as per user role for row "+ row, "Usage History Start Balance masking is not correct as per user role for row "+ row));
        } else {
          assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.getHeaderValue(row, 4).contains("*"), false , "Usage History Start Balance is not masked as per user role for row "+ row, "Usage History Start Balance should not be masked as per user role for row "+ row));
        }

        if (endBalancefieldMaskConfigs.getRoles() != null && !endBalancefieldMaskConfigs.getRoles().isEmpty() && roleDetails.stream()
            .anyMatch(roleName -> endBalancefieldMaskConfigs.getRoles().contains(roleName.getRoleName()))) {
          assertCheck.append(actions.assertEqual_intType(usageHistoryWidget.getHeaderValue(row, 5).replaceAll("[^0-9]", "").trim().length(), endBalancefieldMaskConfigs.getDigitsVisible(), "Usage History End Balance masking is correct as per user role for row " + row, "Usage History End Balance masking is not correct as per user role for row " + row));
        } else {
          assertCheck.append(actions.assertEqual_boolean(usageHistoryWidget.getHeaderValue(row, 5).contains("*"), false , "Usage History End Balance is not masked as per user role for row "+ row, "Usage History End Balance should not be masked as per user role for row "+ row));
        }

      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - checkUsageHistoryMasking" + e.fillInStackTrace(), true);
    }
    actions.assertAllFoundFailedAssert(assertCheck);
  }

}
