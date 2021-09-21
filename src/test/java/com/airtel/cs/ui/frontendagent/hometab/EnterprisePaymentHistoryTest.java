package com.airtel.cs.ui.frontendagent.hometab;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.request.PaymentHistoryESBRequest;
import com.airtel.cs.model.request.PaymentHistoryRequest;
import com.airtel.cs.model.request.PaymentRequest;
import org.apache.commons.lang3.StringUtils;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EnterprisePaymentHistoryTest extends Driver {
  RequestSource api = new RequestSource();

  @BeforeMethod(groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void checkExecution() {
    if (!continueExecutionFA) {
      commonLib.skip("Skipping tests because user NOT able to login Over Portal");
      throw new SkipException("Skipping tests because user NOT able to login Over Portal");
    }
  }

  /**
   * This method  will test the enterprisePayment History API
   */
  @Test(priority = 1, groups = { "SanityTest", "RegressionTest", "ProdTest" })
  public void paymentHistoryCSandDownstreamAPITest() {
    try {
      selUtils.addTestcaseDescription("Verify that enterprise payment  history api is working", "description");
      String accountNo = constants.getValue("enterprise.account.number");
      if(StringUtils.isNotEmpty(accountNo)){
        PaymentHistoryRequest paymentRequest = new PaymentHistoryRequest();
        paymentRequest.setAccountNo(accountNo);
        PaymentHistoryESBRequest paymentHistoryESBRequest = new PaymentHistoryESBRequest();
        paymentHistoryESBRequest.setAccountNo(accountNo);
        paymentHistoryESBRequest.setLimit(constants.getValue(CommonConstants.PAYMENT_REQUEST_LIMIT));
        paymentHistoryESBRequest.setOffset(constants.getValue(CommonConstants.PAYMENT_REQUEST_OFFSET));
        Integer accountInfoStatusCode = api.getEnterprisePaymentHistory(paymentRequest,paymentHistoryESBRequest);
        assertCheck.append(actions.assertEqualIntType(accountInfoStatusCode, 200,
            "Enterprise payment  History  API Status Code Matched and is :" + accountInfoStatusCode,
            "Enterprise payment  History API Status Code NOT Matched and is :" + accountInfoStatusCode, false));
        actions.assertAllFoundFailedAssert(assertCheck);
      } else {
        commonLib.info("Account No not found");
      }
    } catch (Exception e) {
      commonLib.fail("Exception in Method - accountInfoCSandDownstreamAPITest" + e.fillInStackTrace(), true);
    }
  }

}
