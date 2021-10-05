package com.airtel.cs.api.layoutcategorycode;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.request.layout.IssueLayoutRequest;
import org.testng.annotations.Test;

public class IssueLayoutConfigTest extends ApiPrerequisites {

    Integer validCategoryId = 0;


    @Test(priority = 1, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void getCategoryLayoutTest(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/layout API With Category Id: " + getClientCode(list), "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            IssueLayoutRequest layoutConfiguration = api.getLayoutConfiguration(validHeaderList, validCategoryId);
            if (layoutConfiguration.getStatusCode() == 200) {
                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getMessage(), config.getProperty("layoutFetched"), "Response Message Matched Successfully", "Response not Matched and is - " + layoutConfiguration.getMessage()));
                if (layoutConfiguration.getResult() != null)
                    for (int i = 0; i < layoutConfiguration.getResult().size(); i++) {
                        switch (i) {
                            case 0:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel1().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "First Level Issue Layout Name is as Expected", "First Level Issue Layout Name is not expected."));
                                break;
                            case 1:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel2().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Second Level Issue Layout Name is as Expected", "Second Level Issue Layout Name is not expected."));
                                break;
                            case 2:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel3().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Third Level Issue Layout Name is as Expected", "Third Level Issue Layout Name is not expected."));
                                break;
                            case 3:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel4().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Fourth Level Issue Layout Name is as Expected", "Fourth Level Issue Layout Name is not expected."));
                                break;
                            case 4:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel5().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Five Level Issue Layout Name is as Expected", "Fifth Level Issue Layout Name is not expected."));
                                break;
                            case 5:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel6().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Sixth Level Issue Layout Name is as Expected", "sixth Level Issue Layout Name is not expected."));
                                break;
                            case 6:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel7().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Seventh Level Issue Layout Name is as Expected", "seventh Level Issue Layout Name is not expected."));
                                break;
                            case 7:
                                assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(i).getPlaceHolder().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueFieldLabel8().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Eighth Level Issue Layout Name is as Expected", "Eighth Level Issue Layout Name is not expected."));
                                break;
                        }
                    }
            } else {
                commonLib.fail("API response is not expected. Expected 200 but found" + layoutConfiguration.getStatusCode(), false);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getCategoryLayoutTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response When API Request is Invalid", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class, groups = {"RegressionTest"})
    public void getCategoryLayoutInvalidTokenTest(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/layout API With Invalid Token and Valid Category Id", "description");
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            IssueLayoutRequest layoutConfiguration = api.getLayoutConfiguration(restUtils.invalidToken(), validCategoryId);
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getStatus(), "401", "Status Code Matched", "API Response is not 401 as expected"));
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getMessage(), config.getProperty("unauthorized"), "Response Message Matched Successfully", "API Response Message as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getCategoryLayoutInvalidTokenTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response When API Request is Invalid", groups = {"RegressionTest"})
    public void geCategoryLayoutInvalidRequestTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/layout API With valid Token and Invalid Category Id", "description");
            IssueLayoutRequest layoutConfiguration = api.getLayoutConfiguration(validHeaderList, 0);
            assertCheck.append(actions.assertEqualIntType(layoutConfiguration.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched and is - " + layoutConfiguration.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getMessage(), config.getProperty("layoutFetched"), "Response Message Matched Successfully", "API Response Message as not expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - geCategoryLayoutInvalidRequestTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate field label in issue fields and Support for Text Area from the v1/layout api response", groups = {"SanityTest", "RegressionTest"})
    public void testLabelTextAreaSupportIssueLayout() {
        try {
            selUtils.addTestcaseDescription("Hit v1/loyout API,Verify in the response label filed is present or not, Verify field Type = Text Area is there or not", "description");
            IssueLayoutRequest layoutConfiguration = api.getLayoutConfiguration(validHeaderList, Integer.parseInt(constants.getValue(ApplicationConstants.LABEL_TEXTAREA_CATEGORY_ID)));
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(0).getFieldType(), "textArea", "Field Type Text Area Found", "Field Type Text Area Not Found"));
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getMessage(), "Layout fetched successfully.", "Response Message Matched Successfully", "Response Message Not Matched and is - " + layoutConfiguration.getMessage()));
            assertCheck.append(actions.assertEqualStringType(layoutConfiguration.getResult().get(0).getLabel(), "Affected number", "Label Field Exist in Issue Layout API", "Label Field does not exist"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - testLabelTextAreaSupportIssueLayout " + e.getMessage(), false);
        }
    }
}
