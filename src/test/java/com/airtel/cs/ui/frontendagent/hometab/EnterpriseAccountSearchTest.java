package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.ESBRequestSource;
import com.airtel.cs.api.RequestSource;
import com.airtel.cs.driver.Driver;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnterpriseAccountSearchTest extends Driver {

  RequestSource api = new RequestSource();

  @BeforeMethod(groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  /**
   * This method is used to search enterprise account
   */
  @Test(priority = 1, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void enterpriseAccountSearchandDownstreamAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise search account api is working", "description");
      String accountNo = constants.getValue("enterprise.account.number");
      if(StringUtils.isNotEmpty(accountNo)){
        Integer accountInfoStatusCode = api.getEnterpriseSearchAccount(ESBRequestSource.ENTERPRISE_ACCOUNT_NUMBER,accountNo);
        assertCheck.append(actions.assertEqualIntType(accountInfoStatusCode, 200,
            "Enterprise search account API Status Code Matched and is :" + accountInfoStatusCode,
            "Enterprise search account API Status Code NOT Matched and is :" + accountInfoStatusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      } else {
        commonLib.info("Account No not found");
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - enterpriseAccountSearchandDownstreamAPITest" + e.fillInStackTrace(), true);
    }
  }

  /**
   * This method is used to search enterprise corporate msisdn
   */
  @Test(priority = 2, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void enterpriseCorporateMsisdnSearchandDownstreamAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise search account api is working", "description");
      String corporateNo = constants.getValue("enterprise.corporate.msisdn");
      if(StringUtils.isNotEmpty(corporateNo)){
        Integer accountInfoStatusCode = api.getEnterpriseSearchAccount(ESBRequestSource.CORPORATE_CUSTOMER_NUMBER,corporateNo);
        assertCheck.append(actions.assertEqualIntType(accountInfoStatusCode, 200,
            "Enterprise search account API Status Code Matched and is :" + accountInfoStatusCode,
            "Enterprise search account API Status Code NOT Matched and is :" + accountInfoStatusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      } else {
        commonLib.info("Enterprise Corporate Msisdn not found");
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - enterpriseAccountSearchandDownstreamAPITest" + e.fillInStackTrace(), true);
    }
  }
}