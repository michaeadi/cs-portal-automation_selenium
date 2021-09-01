package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.PaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnterpriseAccountInfoWidgetTest extends Driver {

  RequestSource api = new RequestSource();

  @BeforeMethod(groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  /**
   * This method is used to hit enterprise account info widget api
   */
  @Test(priority = 1, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void accountInfoCSandDownstreamAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise account info api is working", "description");
      String accountNo = constants.getValue("enterprise.Account.No");
      if(StringUtils.isNotEmpty(accountNo)){
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAccountNo(accountNo);
        paymentRequest.setLimit(constants.getValue(CommonConstants.PAYMENT_REQUEST_LIMIT));
        paymentRequest.setOffset(constants.getValue(CommonConstants.PAYMENT_REQUEST_OFFSET));
        Integer accountInfoStatusCode = api.getEnterprisePostpaidAccountInformation(accountNo, paymentRequest);
        assertCheck.append(actions.assertEqualIntType(accountInfoStatusCode, 200,
            "Enterprise account info API Status Code Matched and is :" + accountInfoStatusCode,
            "Enterprise account info API Status Code NOT Matched and is :" + accountInfoStatusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      } else {
        commonLib.info("Account No not found");
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - accountInfoCSandDownstreamAPITest" + e.fillInStackTrace(), true);
    }
  }
}
