package com.airtel.cs.api.layoutcategorycode;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.commonutils.dataproviders.databeans.FtrDataBeans;
import com.airtel.cs.commonutils.dataproviders.databeans.NftrDataBeans;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.model.cs.request.categoryhierarchy.CategoryHierarchyRequest;
import org.testng.annotations.Test;

public class ParentCategoryTest extends ApiPrerequisites {

    Integer validCategoryId = 0;


    @Test(priority = 1, description = "Validate API Response Test is Successful", dataProvider = "FTRIssue", dataProviderClass = DataProviders.class,groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void getParentCategoryTest(FtrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/parent/categories API With Category Id: " + list.getIssueCode(), "description");
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            CategoryHierarchyRequest parentCategory = api.getParentCategoryId(validHeaderList, validCategoryId);
            for (int i = Integer.parseInt(clientConfig.getFirstCategoryLevel()); i <= Integer.parseInt(clientConfig.getLastCategoryLevel()); i++) {
                switch (i - 1) {
                    case 0:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssue().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "First Level Category Name Matced", "First Level Category Name is not expected"));
                        break;
                    case 1:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Second Level Category Name Matched", "Second Level Category Name is not as expected"));
                        break;
                    case 2:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueSubType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Third Level Category Name Matched", "Third Level Category Name is not as expected"));
                        break;
                    case 3:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueSubSubType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Fourth Level Category Name Matched", "Fourth Level Category Name is not as expected"));
                        break;
                    case 4:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueCode().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Fifth Level Category Name Matched", "Fifth Level Category Name is not as expected"));
                        break;
                }

            }
            assertCheck.append(actions.assertEqualStringType(parentCategory.getStatus(), "SUCCESS", "Status Matched Successfully", "Status Not Matched and is - " + parentCategory.getStatus()));

            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getParentCategoryTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response Test is Successful", dataProvider = "NFTRIssue", dataProviderClass = DataProviders.class,groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void getNFTRParentCategoryTest(NftrDataBeans list) {
        try {
            selUtils.addTestcaseDescription("Validate /v1/parent/categories API With Category Id: " + list.getIssueCode(), "description");
            ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
            setLastCategoryIntoMap();
            validCategoryId = ids.get(getClientCode(list).toLowerCase().trim());
            CategoryHierarchyRequest parentCategory = api.getParentCategoryId(validHeaderList, validCategoryId);
            for (int i = Integer.parseInt(clientConfig.getFirstCategoryLevel()); i <= Integer.parseInt(clientConfig.getLastCategoryLevel()); i++) {
                switch (i - 1) {
                    case 0:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssue().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "First Level Category Name Matched", "First Level Category Name is not expected"));
                        break;
                    case 1:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Second Level Category Name Matched", "Second Level Category Name is not expected"));
                        break;
                    case 2:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueSubType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Third Level Category Name Matched", "Third Level Category Name is not expected"));
                        break;
                    case 3:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueSubSubType().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Fourth Level Category Name Matched", "Fourth Level Category Name is not expected"));
                        break;
                    case 4:
                        assertCheck.append(actions.assertEqualStringType(parentCategory.getResult().get(String.valueOf(i)).get(0).getCategoryName().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), list.getIssueCode().toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", ""), "Fifth Level Category Name Matched", "Fifth Level Category Name is not expected"));
                        break;
                }
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getNFTRParentCategoryTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 3, description = "Validate API Response When API Request is Invalid",groups = {"RegressionTest"})
    public void getParentCategoryInvalidTokenTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/parent/categories API With Invalid Token and Valid Category Id", "description");
            CategoryHierarchyRequest parentCategory = api.getParentCategoryId(restUtils.invalidToken(), validCategoryId);
            assertCheck.append(actions.assertEqualStringType(parentCategory.getStatus(), "401", "Status Matched", "Status Not Matched and is - " + parentCategory.getStatus()));
            assertCheck.append(actions.assertEqualStringType(parentCategory.getMessage(), constants.getValue("unauthorized"), "API Response Message Matched", "API Response Message not as expected and is - " + parentCategory.getMessage()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getParentCategoryInvalidTokenTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 4, description = "Validate API Response When API Request is Invalid",groups = {"RegressionTest"})
    public void getParentCategoryInvalidRequestTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/parent/categories API With valid Token and Invalid Category Id", "description");
            CategoryHierarchyRequest parentCategory = api.getParentCategoryId(validHeaderList, 0);
            assertCheck.append(actions.assertEqualIntType(parentCategory.getStatusCode(), 4052, "Status Matched Successfully", "Status Not Matched and is - " + parentCategory.getStatusCode()));
            assertCheck.append(actions.assertEqualStringType(parentCategory.getMessage(), "Category not found.", "Response Message Matched", "Response Message Not Matched and is - " + parentCategory.getMessage()));
            actions.assertAllFoundFailedAssert(assertCheck);
        }catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - getParentCategoryInvalidRequestTest " + e.getMessage(), false);
        }
    }
}
