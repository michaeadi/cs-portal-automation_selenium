package com.airtel.cs.common.actions;

import com.airtel.cs.commonutils.commonlib.CommonLib;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseActions {
    public Boolean assertFlag = null;
    CommonLib commonLib = new CommonLib();
    SoftAssert soft = new SoftAssert();

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

    public Boolean assertEqual_boolean(boolean actual, boolean expected) {
        return assertEqual_boolean(actual, expected, "");
    }

    public Boolean assertEqual_boolean(boolean actual, boolean expected, String description) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(description);
            assertFlag = true;
        } catch (Throwable ex) {
            // org.testng.Assert.fail("expected and actual result do not match");
            commonLib.fail("Assertion FAILED - " + ex.getMessage(), true);
        }
        return assertFlag;
    }

    public Boolean assertEqual_boolean(boolean actual, boolean expected, String passDescription, String failDescription) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passDescription);
            assertFlag = true;
        } catch (Throwable ex) {
            // org.testng.Assert.fail("expected and actual result do not match");
            commonLib.fail("Assertion FAILED - " + ex.getMessage() + "-" + failDescription, true);
        }
        return assertFlag;
    }

    public boolean assertAllFoundFailedAssert(StringBuilder AssertResults) {
        // ACTION PENDING - REQUIRE TO CALL THIS TO @AFTERMETHOD
        if (AssertResults.toString().equals("") || AssertResults.toString().contains("false")) {
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
     * @param actual
     * @param expected
     */
    public Boolean assertEqual_intType(int actual, int expected, String passDescription, String failDescription, boolean requirescreenshots) {
        assertFlag = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passDescription);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(failDescription, requirescreenshots);
        }
        return assertFlag;
    }

    public Boolean assertEqual_intType(int actual, int expected, String passDescription, String failDescription) {
        return assertEqual_intType(actual, expected, passDescription, failDescription, true);
    }

    /**
     * Verify.
     *
     * @param actual   the actual
     * @param expected the expected
     */
    public Boolean assertEqual_stringType(String actual, String expected, String passMessage, String failMessage) {
        return assertEqual_stringType(actual, expected, passMessage, failMessage, true);
    }

    public Boolean assertEqual_stringType(String actual, String expected, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    public Boolean assertEqual_intType(int actual, int expected) {

        return assertEqual_intType(actual, expected, "", "");
    }

    public Boolean assertEqual_stringNotNull(String actual, String passMessage, String failMessage) {
        return assertEqual_stringNotNull(actual, passMessage, failMessage, true);
    }

    public Boolean assertEqual_stringNotNull(String actual, String passMessage, String failMessage, boolean requiredScreenshot) {
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
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    public Boolean assertEqual_intNotNull(int actual, String passMessage, String failMessage) {
        return assertEqual_intNotNull(actual, passMessage, failMessage, true);
    }

    public Boolean assertEqual_intNotNull(int actual, String passMessage, String failMessage, boolean requiredScreenshot) {
        assertFlag = false;
        boolean shouldCapturescreenshot = true;
        try {
            if (!requiredScreenshot) {
                shouldCapturescreenshot = false;
            }
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    /**
     * Verify.
     *
     * @param actual   the actual
     * @param expected the expected
     */
    public Boolean assertNotEqual_stringType(String actual, String expected, String passMessage, String failMessage) {
        assertFlag = false;
        try {
            Assert.assertNotEquals(actual, expected, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, true);

        }
        return assertFlag;
    }

    public Boolean assertNotEqual_intType(int actual, int expected, String passMessage, String failMessage) {
        assertFlag = false;
        try {
            Assert.assertNotEquals(actual, expected, passMessage);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, true);
        }
        return assertFlag;
    }

    public Boolean assertNotEqual_intType(int actual, int expected, String passMessage) {
        return assertNotEqual_intType(actual, expected, "", "");
    }

    /**
     * METHOD TO ASSERT STATUS CODE -
     */
    public boolean assertStatusCode(Response response, int expectedStatusCode, boolean requireToPrintResponseForPassCase) {
        boolean isAsserMatched = false;
        try {
            //ValidatableResponse statuscodeResult = response.then().assertThat().statusCode(expectedStatusCode);

            int actualStatusCode = response.getStatusCode();
            Boolean isAssertPass = assertEqual_intType(actualStatusCode, expectedStatusCode, "Status Code Matched - " + actualStatusCode,
                    "Status Code Not Matched" + "</br>" + "Expected <" + expectedStatusCode + "> but found <" + actualStatusCode + ">", false);
            ResponseBody body = response.getBody();
            if (!isAssertPass) {
                commonLib.fail("Invalid Response - " + "<pre>" + body.prettyPrint() + "</pre>", true);
            } else if (requireToPrintResponseForPassCase) {
                commonLib.info("Response - " + "<pre>" + body.prettyPrint() + "</pre>", false);
            }
            isAsserMatched = isAssertPass;
        } catch (AssertionError e) {
            commonLib.fail("Status Code Not Matched" + "</br>" + e.getMessage(), true);
        }
        return isAsserMatched;
    }

    public Boolean assertEqual_stringStatusCode(String actual, String expected, String passMessage, String failMessage) {
        assertFlag = false;
        boolean shouldCapturescreenshot = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, shouldCapturescreenshot);
        }
        return assertFlag;
    }

    public Boolean assertEqual_intergerStatusCode(int actual, int expected, String passMessage, String failMessage) {
        assertFlag = false;
        boolean shouldCapturescreenshot = false;
        try {
            Assert.assertEquals(actual, expected);
            commonLib.pass(passMessage);
            assertFlag = true;
        } catch (Throwable ex) {
            commonLib.fail(ex.getMessage() + "{<br />" + failMessage, shouldCapturescreenshot);
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
            Boolean resultOfAssertion = assertEqual_stringType(actualValue, expectedValue, passMessage, failMessage, false);

            ResponseBody body = response.getBody();
            if (!resultOfAssertion) {
                commonLib.fail("Invalid Response - " + "<pre>" + body.prettyPrint() + "</pre>", true);
            }
        } catch (AssertionError e) {
            commonLib.fail("Status Code Not Matched" + "/br" + e.getMessage(), true);
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
                return assertEqual_stringType(uiValue.toLowerCase().trim(), apiValue.toLowerCase().trim(), passMessage, failMessage);
            } else {
                return assertEqual_stringType(uiValue, "-", passMessage, failMessage);
            }
        } catch (Exception e) {
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
                return assertEqual_intType(Integer.parseInt(uiValue), apiValue, passMessage, failMessage);
            } else {
                return assertEqual_stringType(uiValue, "-", passMessage, failMessage);
            }
        } catch (Exception e) {
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
        return assertEqual_stringType(actual.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), expected.replaceAll("[^a-zA-Z]+", "").toLowerCase().trim(), passMessage, failMessage);
    }
}
