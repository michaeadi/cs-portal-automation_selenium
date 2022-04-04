package com.airtel.cs.ui.ngpsb;

import com.airtel.cs.api.PsbRequestSource;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.cs.response.psb.cs.clmdetails.CLMDetailsResponse;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Log4j2
public class DemoGraphicWidgetTest extends Driver {
    private static String customerNumber = null;
    PsbRequestSource api = new PsbRequestSource();
    CLMDetailsResponse clmDetails;
    String space = " ";


    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.CUSTOMER_TIER1_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            clmDetails = api.getCLMDetails(customerNumber);
            assertCheck.append(actions.assertEqualIntType(clmDetails.getStatusCode(), 200, "CLM Details API Status Code Matched and is :" + clmDetails.getStatusCode(), "CLM Details API Status Code NOT Matched and is :" + clmDetails.getStatusCode(), false));
            if (clmDetails.getStatusCode() == 200) {
                boolean pageLoaded = pages.getDemographicWidget().isPageLoaded(clmDetails);
                if (!pageLoaded)
                    continueExecutionFA = false;
            } else
                commonLib.warning("Clm Details API is not working");
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
    }


    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testCustomerDetails() {
        try {
            selUtils.addTestcaseDescription("Validate Customer Name, Validate ", "description");
            assertCheck.append(actions.assertEqualStringType(pages.getDemographicWidget().getMiddleAuuid(), loginAUUID, "Auuid is visible at the middle of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the middle of the Demo Graphic Widget"));
            assertCheck.append(actions.assertEqualStringType(pages.getDemographicWidget().getFooterAuuid(), loginAUUID, "Auuid is visible at the footer of the Demo Graphic Widget and is correct", "Auuid is NOT visible at the footer of the Demo Graphic Widget"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isCustomerNameHeaderVisible(), true, "Customer Name header is visible", "Customer Name header is NOT visible"));
            final String customerName = pages.getDemographicWidget().getCustomerName();
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getCustomerName(), clmDetails.getResult().getFirstName() + space + clmDetails.getResult().getLastName(), "Customer Name is as Expected", "Customer Name is not as Expected"));
            if (customerName.equalsIgnoreCase("-")) {
                commonLib.warning("Customer Name is not available so unable to display customer details");
            } else {
                pages.getDemographicWidget().hoverOnCustomerInfoIcon();
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isGenderHeaderVisible(), true, "Gender header is visible", "Customer Name header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isDobHeaderVisible(), true, "DOB header is visible", "DOB header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPobHeaderVisible(), true, "Place of Birth header is visible", "Place of Birth header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isNationalityHeaderVisible(), true, "Nationality header is visible", "Nationality header is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isMaidenNameHeaderVisible(), true, "Mothers Maiden Name header is visible", "Mothers Maiden Name is NOT visible"));
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isCustomerTypeHeaderVisible(), true, "Customer Type header is visible", "Customer Type header is NOT visible"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getGender(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getGender()), "Gender is as Expected", "Gender is not as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getDob(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDob()), "DOB is as Expected", "DOB is not as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPob(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getPlaceOfBirth()), "Place of birth is as Expected", "Place of birth is not as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getNationality(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getNationality()), "Nationality is as Expected", "Nationality is not as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getMothersMaidenName(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getMothersMaidenName()), "Mothers maiden name is as Expected", "Mothers maiden name is not as Expected"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getCustomerType(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getCustomerType()), "Customer Type is as Expected", "Customer Type is not as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testCustomerDetails " + e, true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testPrimaryAndSecondaryIdType() {
        try {
            selUtils.addTestcaseDescription("Validate Primary Id Type & Number, Validate Secondary Id Type & Number", "description");
           /*
           Validating Primary Id Type and Number
            */
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPrimaryIdTypeHeaderVisible(), true, "Primary Id Type header is visible", "Primary Id Type header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPrimaryIdType(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getPrimaryIdType()), "Primary Id Type is same as Expected", "Primary Id Type is not same as Expected"));
            if (pages.getDemographicWidget().getPrimaryIdType().equalsIgnoreCase("-")) {
                commonLib.warning("Primary Id Type is not available so unable to display Primary Id Number");
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPrimaryIdNumberHeaderVisible(), true, "Primary Id Number header is visible", "Primary Id Number header is NOT visible"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPrimaryIdNumber(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getPrimaryIdType()), "Primary Id Number is same as Expected", "Primary Id Number is not same as Expected"));
            }
            /*
           Validating Secondary Id Type and Number
            */
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isSecondaryIdTypeHeaderVisible(), true, "Secondary Id Type header is visible", "Primary Id Type header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getSecondaryIdType(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getPrimaryIdType()), "Secondary Id Type is same as Expected", "Primary Id Type is not same as Expected"));
            if (pages.getDemographicWidget().getPrimaryIdType().equalsIgnoreCase("-")) {
                commonLib.warning("Secondary Id Type is not available so unable to display Secondary Id Number is");
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isSecondaryIdNumberHeaderVisible(), true, "Secondary Id Number header is visible", "Secondary Id Number header is NOT visible"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getSecondaryIdNumber(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getSecondaryIdType()), "Secondary Id Number is same as Expected", "Secondary Id Number is not same as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in method - testEmailIdAndPrimarySecondaryIdType" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 4, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testEmailIdAndAgent() {
        try {
            selUtils.addTestcaseDescription("Validate Email Id , Validate is User an Agent", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isEmailIdHeaderVisible(), true, "Email Id header is visible", "Email Id header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getEmailId(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getEmailId()), "Email Id is same as Expected", "Email id is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isUserAgentHeaderVisible(), true, "Is user An Agent header is visible", "Is user An Agent  header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getIsUserAgent(), clmDetails.getResult().getDetails().get(0).getIsUser(), "Is user An Agent  is same as Expected", "Is user An Agent is not same as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testEmailIdAndAgent " + e, true);
        }
    }

    @Test(priority = 5, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testAddress() {
        try {
            selUtils.addTestcaseDescription("Validate Address", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isAddressHeaderVisible(), true, "Address header is visible", "Address header is NOT visible"));
            //assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getAddress(), clmDetails.getResult().getIsPinSet(), "Address is same as Expected", "Address is not same as Expected"));
            if (pages.getDemographicWidget().getAddress().equalsIgnoreCase("-")) {
                commonLib.warning("Address is not available so unable to display ");
            } else {
                assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPrimaryIdNumberHeaderVisible(), true, "Primary Id Number header is visible", "Primary Id Number header is NOT visible"));
                assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPrimaryIdNumber(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getPrimaryIdType()), "Primary Id Number is same as Expected", "Primary Id Number is not same as Expected"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testAddress " + e, true);
        }
    }

    @Test(priority = 6, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testCustomerIdAndCategory() {
        try {
            selUtils.addTestcaseDescription("Validate Customer Id, Validate Customer Category", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isCustomerIdHeaderVisible(), true, "Customer Id header is visible", "Email Id header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getCustomerId(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getCustomerId()), "Customer Id is same as Expected", "Customer Id is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isCustomerCategoryHeaderVisible(), true, "Customer Category header is visible", "Customer Category  header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getCustomerCategory(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getEmailId()), "Customer Category  is same as Expected", "Customer Category  is not same as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testCustomerIdAndCategory " + e, true);
        }
    }


    @Test(priority = 7, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"}, dependsOnMethods = {"openCustomerInteraction"})
    public void testPinSetAndReset() {
        try {
            selUtils.addTestcaseDescription("Validate Pin Set, Validate Pin Reset", "description");
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPinResetHeaderVisible(), true, "Pin Reset header is visible", "Pin Reset header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPinReset(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getIsPinReset()), "Pin Reset is same as Expected", "Pin Reset is not same as Expected"));
            assertCheck.append(actions.assertEqualBoolean(pages.getDemographicWidget().isPinSetHeaderVisible(), true, "Pin Set header is visible", "Pin Set header is NOT visible"));
            assertCheck.append(actions.matchUiAndAPIResponse(pages.getDemographicWidget().getPinSet(), pages.getDemoGraphicPage().getKeyValueAPI(clmDetails.getResult().getDetails().get(0).getIsPinSet()), "Pin Set  is same as Expected", "Pin Set is not same as Expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NoSuchElementException | TimeoutException | NullPointerException e) {
            commonLib.fail("Exception in method - testPinSetAndReset " + e, true);
        }
    }
}
