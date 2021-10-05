package com.airtel.cs.commonutils.actions;

import com.airtel.cs.commonutils.commonlib.CommonLib;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;

public class BaseActions {
    public Boolean assertFlag = null;
    CommonLib commonLib = new CommonLib();
    private static final String BREAK = "{<br />";
    private static final String PRE = "<pre>";
    private static final String STATUS_NOT_MATCHED = "Status Code Not Matched";

    /**
     * Verify status code.
     *
     * @param statuscode the statuscode
     */
    public void verifyStatusCode(int statuscode) {
        Assert.assertEquals(statuscode, 200, "Status check failed status is:" + statuscode);
    }

    /**
     * Verify.
     *
     * @param actual   the actual
     * @param expected the expected
     */

    public Boolean assertEqualBoolean(boolean actual, boolean expected) {
        return assertEqualBoolean(actual, expected, "");
    }

    public Boolean assertEqualBoolean(boolean actual, boolean expected, String description) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(description);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail("Assertion FAILED - " + ex.getMessage(), true);
        }
        return assertFlag;
    }

    public Boolean assertEqualBoolean(boolean actual, boolean expected, String passDescription, String failDescription) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passDescription);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail("Assertion FAILED - " + ex.getMessage() + "-" + failDescription, true);
        }
        return assertFlag;
    }


    /**
     * This method use to assert actual and expected value is same or not and check hard assert to do or not
     *
     * @param actual          the actual
     * @param expected        the expected
     * @param passDescription the pass message
     * @param failDescription the fail message
     * @param isHardAssert    the true/false
     * @return true
     * @throws AssertionError assertion failed
     */
    public Boolean assertEqualBoolean(boolean actual, boolean expected, String passDescription, String failDescription, boolean isHardAssert) {
        boolean flag = assertEqualBoolean(actual, expected, passDescription, failDescription);
        if (!flag && isHardAssert) {
            throwHardAssert();
        }
        return true;
    }

    public boolean assertAllFoundFailedAssert(StringBuilder assertResults) {
        // ACTION PENDING - REQUIRE TO CALL THIS TO @AFTERMETHOD
        if (assertResults.toString().equals("") || assertResults.toString().contains("false")) {
            Assert.fail("Some Assertions failed in this testcase");
            return true;
        } else
            commonLib.pass("All Assertion Verified and are Passed");
        return false;
    }

    /**
     * Verify response time.
     * <p>
     * <p>
     * /** Verify response body.
     *
     * @param actual      the actual
     * @param expected    the expected
     * @param description the description
     */
    public void verifyResponseBody(String actual, String expected, String description) {
        Assert.assertEquals(actual, expected, description);

    }

    /**
     * Verify response body.
     *
     * @param actual      the actual
     * @param expected    the expected
     * @param description the description
     */
    public void verifyResponseBody(boolean actual, boolean expected, String description) {
        Assert.assertEquals(actual, expected, description);

    }

    /**
     * Verify response body.
     *
     * @param actual      the actual
     * @param expected    the expected
     * @param description the description
     */
    public void verifyResponseBody(int actual, int expected, String description) {
        Assert.assertEquals(actual, expected, description);

    }

    /**
     * Verify
     *
     * @param actual   The Actual Value
     * @param expected The Expected Value
     */
    public Boolean assertEqualIntType(int actual, int expected, String passDescription, String failDescription, boolean requirescreenshots) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passDescription);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(failDescription, requirescreenshots);
        }
        return assertFlag;
    }

    public Boolean assertEqualIntType(int actual, int expected, String passDescription, String failDescription) {
        return assertEqualIntType(actual, expected, passDescription, failDescription, true);
    }

    /**
     * This method use to assert actual and expected value is same or not and check hard assert to do or not
     *
     * @param actual          the actual
     * @param expected        the expected
     * @param passDescription the pass message
     * @param failDescription the fail message
     * @param isHardAssert    the true/false
     * @return true
     * @throws AssertionError assertion failed
     */
    public Boolean assertEqualIntType(int actual, int expected, String passDescription, String failDescription, boolean requiredScreenShot, boolean isHardAssert) {
        boolean flag = assertEqualIntType(actual, expected, passDescription, failDescription, requiredScreenShot);
        if (!flag && isHardAssert) {
            throwHardAssert();
        }
        return true;
    }

    /**
     * Verify.
     *
     * @param actual   the actual
     * @param expected the expected
     */
    public Boolean assertEqualStringType(String actual, String expected, String passMessage, String failMessage) {
        return assertEqualStringType(actual, expected, passMessage, failMessage, true);
    }

    public Boolean assertEqualStringType(String actual, String expected, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    /**
     * This method use to assert actual and expected value is same or not and check hard assert to do or not
     *
     * @param actual          the actual
     * @param expected        the expected
     * @param passDescription the pass message
     * @param failDescription the fail message
     * @param isHardAssert    the true/false
     * @return true
     * @throws AssertionError assertion failed
     */
    public Boolean assertEqualStringType(String actual, String expected, String passDescription, String failDescription, boolean requiredScreenShot, boolean isHardAssert) {
        boolean flag = assertEqualStringType(actual, expected, passDescription, failDescription, requiredScreenShot);
        if (!flag && isHardAssert) {
            throwHardAssert();
        }
        return true;
    }

    public Boolean assertEqualIntType(int actual, int expected) {

        return assertEqualIntType(actual, expected, "", "");
    }

    public Boolean assertEqualStringNotNull(String actual, String passMessage, String failMessage) {
        return assertEqualStringNotNull(actual, passMessage, failMessage, true);
    }

    public Boolean assertEqualStringNotNull(String actual, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            Assert.assertNotNull(actual, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    public Boolean assertEqualIntNotNull(Integer actual, String passMessage, String failMessage) {
        return assertEqualIntNotNull(actual, passMessage, failMessage, true);
    }

    public Boolean assertEqualIntNotNull(Integer actual, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            if (actual != null) {
                commonLib.pass(passMessage);
                assertFlag = true;
            }
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    /**
     * Verify.
     *
     * @param actual   the actual
     * @param expected the expected
     */
    public Boolean assertNotEqualStringType(String actual, String expected, String passMessage, String failMessage) {
        assertFlag = false;
        try {
            Assert.assertNotEquals(actual, expected, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, true);

        }
        return assertFlag;
    }

    public Boolean assertNotEqualIntType(int actual, int expected, String passMessage, String failMessage) {
        assertFlag = false;
        try {
            Assert.assertNotEquals(actual, expected, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, true);
        }
        return assertFlag;
    }

    public Boolean assertNotEqualIntType(int actual, int expected, String passMessage) {
        return assertNotEqualIntType(actual, expected, "", "");
    }

    /**
     * METHOD TO ASSERT STATUS CODE -
     */
    public boolean assertStatusCode(Response response, int expectedStatusCode, boolean requireToPrintResponseForPassCase) {
        boolean isAsserMatched = false;
        try {
            int actualStatusCode = response.getStatusCode();
            boolean isAssertPass = assertEqualIntType(actualStatusCode, expectedStatusCode, "Status Code Matched - " + actualStatusCode,
                    STATUS_NOT_MATCHED + BREAK + "Expected <" + expectedStatusCode + "> but found <" + actualStatusCode + ">", false);
            ResponseBody body = response.getBody();
            if (!isAssertPass) {
                commonLib.fail("Invalid Response - " + PRE + body.prettyPrint() + PRE, true);
            } else if (requireToPrintResponseForPassCase) {
                commonLib.info("Response - " + PRE + body.prettyPrint() + PRE, false);
            }
            isAsserMatched = isAssertPass;
        } catch (AssertionError e) {
            commonLib.fail(STATUS_NOT_MATCHED + BREAK + e.getMessage(), true);
        }
        return isAsserMatched;
    }

    public Boolean assertEqualStringStatusCode(String actual, String expected, String passMessage, String failMessage) {
        assertFlag = false;
        boolean shouldCapturescreenshot = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    public Boolean assertEqualIntergerStatusCode(int actual, int expected, String passMessage, String failMessage) {
        assertFlag = false;
        boolean shouldCapturescreenshot = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Exception | AssertionError ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    /**
     * METHOD TO ASSERT STATUS CODE -
     */
    public void assertResponseValue(Response response, String expectedValue, String actualValue, String keyName) {
        try {
            String passMessage = "For key : | " + keyName + " | " + "</br>" + " Expected value correctly matched with Actual";
            String failMessage = "For key : | " + keyName + " | " + "</br>" + " Expected value NOT matched with Actual";
            boolean resultOfAssertion = assertEqualStringType(actualValue, expectedValue, passMessage, failMessage, false);

            ResponseBody body = response.getBody();
            if (!resultOfAssertion) {
                commonLib.fail("Invalid Response - " + PRE + body.prettyPrint() + "</pre>", true);
            }
        } catch (AssertionError e) {
            commonLib.fail(STATUS_NOT_MATCHED + "/br" + e.getMessage(), true);
        }
    }

    /**
     * This method use to assert API Response and display value on UI
     *
     * @param uiValue     value display on UI
     * @param apiValue    api response value
     * @param passMessage in case of both ui and API value same
     * @param failMessage in case of both ui and API value not same
     */
    public Boolean matchUiAndAPIResponse(String uiValue, String apiValue, String passMessage, String failMessage) {
        try {
            if (apiValue != null) {
                return assertEqualStringType(uiValue.toLowerCase().trim(), apiValue.toLowerCase().trim(), passMessage, failMessage);
            } else {
                return assertEqualStringType(uiValue, "-", passMessage, failMessage);
            }
        } catch (Exception | AssertionError e) {
            commonLib.fail("Exception : Not able to match ui and api response" + e.fillInStackTrace(), true);
        }
        return false;
    }

    /**
     * This method use to assert API Response and display value on UI
     *
     * @param uiValue     value display on UI
     * @param apiValue    api response value
     * @param passMessage in case of both ui and API value same
     * @param failMessage in case of both ui and API value not same
     */
    public Boolean matchUiAndAPIResponse(String uiValue, Integer apiValue, String passMessage, String failMessage) {
        try {
            if (apiValue != null && !uiValue.equalsIgnoreCase("-")) {
                return assertEqualIntType(Integer.parseInt(uiValue), apiValue, passMessage, failMessage);
            } else {
                return assertEqualStringType(uiValue, "-", passMessage, failMessage);
            }
        } catch (Exception | AssertionError e) {
            commonLib.fail("Exception : Not able to match ui and api response" + e.fillInStackTrace(), true);
        }
        return false;
    }

    /**
     * This method match the actual and expected string and ignore all the characters except alphabets
     *
     * @param actual      the actual
     * @param expected    the expected
     * @param passMessage the pass message
     * @param failMessage the fail message
     * @return true/false
     */
    public Boolean matchStringIgnoreSpecialChar(String actual, String expected, String passMessage, String failMessage) {
        return assertEqualStringType(actual.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), expected.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), passMessage, failMessage);
    }

    /**
     * This method use to assert API Response and display value on UI and check hard assert to do or not
     *
     * @param actual          the actual
     * @param expected        the expected
     * @param passDescription the pass message
     * @param failDescription the fail message
     * @param isHardAssert    the true/false
     * @return true
     * @throws AssertionError assertion failed
     */
    public Boolean matchUiAndAPIResponse(String actual, String expected, String passDescription, String failDescription, boolean isHardAssert) {
        boolean flag = matchUiAndAPIResponse(actual, expected, passDescription, failDescription);
        if (!flag && isHardAssert) {
            throwHardAssert();
        }
        return true;
    }

    /**
     * This method is use to throw assertion error
     *
     * @throws AssertionError assertion failed
     */
    public void throwHardAssert() {
        throw new AssertionError("Important::Assertion Failed");
    }

    public Boolean assertEqualBooleanNotNull(Boolean actual, String passMessage, String failMessage) {
        return assertEqualBooleanNotNull(actual, passMessage, failMessage, false);
    }

    public Boolean assertEqualBooleanNotNull(Boolean actual, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            Assert.assertNotNull(actual, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + BREAK + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }
}
