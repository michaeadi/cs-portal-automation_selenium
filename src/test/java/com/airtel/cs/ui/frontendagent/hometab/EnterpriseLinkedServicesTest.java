package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.EnterpriseLinkedServiceRequest;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnterpriseLinkedServicesTest extends Driver {

  RequestSource api = new RequestSource();

  @BeforeMethod(groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  /**
   * This method is used to hit enterprise linked services api
   */
  @Test(priority = 1, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void enterpriseLinkedServiceCSandDownstreamAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise linked service api is working", "description");
      String accountNo = constants.getValue("enterprise.Account.No");
      if(StringUtils.isNotEmpty(accountNo)){
        EnterpriseLinkedServiceRequest enterpriseLinkedServiceRequest = new EnterpriseLinkedServiceRequest();
        enterpriseLinkedServiceRequest.setAccountNo(accountNo);
        enterpriseLinkedServiceRequest.setLineType("POSTPAID");
        enterpriseLinkedServiceRequest.setServiceType("MOBILE");
        Integer statusCode = api.getEnterpriseLinkedServices(enterpriseLinkedServiceRequest);
        assertCheck.append(actions.assertEqualIntType(statusCode, 200,
            "Enterprise linked services  API Status Code Matched and is :" + statusCode,
            "Enterprise linked services  API Status Code NOT Matched and is :" + statusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      } else {
        commonLib.info("Account No not found");
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - enterpriseLinkedServiceCSandDownstreamAPITest" + e.fillInStackTrace(), true);
    }
  }
}

