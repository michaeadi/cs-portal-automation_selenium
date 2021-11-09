package com.airtel.cs.ui.frontendagent.viewhistorytab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.driver.Driver;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnterpriseTicketHistoryTest extends Driver {

  RequestSource api = new RequestSource();

  @BeforeMethod(groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  /**
   * This method is used to hit enterprise ticket history api
   */
  @Test(priority = 1, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void enterpriseTicketHistoryAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise ticket history api is working", "description");
      String accountNo = constants.getValue("enterprise.account.number");
      if (StringUtils.isNotEmpty(accountNo)) {
        Integer statusCode = api.getEnterpriseTicketHistory(accountNo);
        assertCheck.append(
            actions.assertEqualIntType(statusCode, 200, "Enterprise ticket history API Status Code Matched and is :" + statusCode,
                "Enterprise ticket history API Status Code NOT Matched and is :" + statusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - enterpriseTicketHistoryAPITest" + e.fillInStackTrace(), true);
    }
  }
}
