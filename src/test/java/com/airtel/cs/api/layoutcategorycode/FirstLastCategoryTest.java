package com.airtel.cs.api.layoutcategorycode;


import com.airtel.cs.common.prerequisite.ApiPrerequisites;
import com.airtel.cs.commonutils.dataproviders.databeans.ClientConfigDataBean;
import com.airtel.cs.model.request.categoryhierarchy.CategoryHierarchyRequest;
import com.airtel.cs.model.request.issue.CategoryHierarchy;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

@Log4j2
public class FirstLastCategoryTest extends ApiPrerequisites {

    @Test(priority = 1, description = "Validate API Response Test is Successful", groups = {"SanityTest", "RegressionTest","ProdTest"})
    public void firstLastCategoryTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/firstlast/categories API With Valid Request", "description");
            CategoryHierarchyRequest firstLastCategory = api.firstLastCategoryHierarchyTest(validHeaderList);
            if (firstLastCategory.getStatusCode().equals(200)) {
                ClientConfigDataBean clientConfig = data.getClientConfig().get(0);
                ArrayList<CategoryHierarchy> firstLevelCategories = firstLastCategory.getResult().get(clientConfig.getFirstCategoryLevel());
                clientConfig.getFirstCategoryLevel();
                ArrayList<CategoryHierarchy> lastLevelCategories = firstLastCategory.getResult().get(clientConfig.getLastCategoryLevel());
                if (firstLevelCategories.size() != 0 || lastLevelCategories.size() != 0) {
                    if (Integer.parseInt(clientConfig.getFirstCategoryLevel()) == Integer.parseInt(clientConfig.getLastCategoryLevel())) {
                        restUtils.printInfoLog("First & Last are Category Same.");
                        for (CategoryHierarchy level : firstLevelCategories) {
                            level.getLevel().equals(Integer.parseInt(clientConfig.getFirstCategoryLevel()));
                        }
                    } else {
                        for (Map.Entry mapElement : firstLastCategory.getResult().entrySet()) {
                            String key = (String) mapElement.getKey();
                            String value = mapElement.getValue().toString();
                            if (Integer.parseInt(key) != Integer.parseInt(clientConfig.getFirstCategoryLevel()) && Integer.parseInt(key) != Integer.parseInt(clientConfig.getLastCategoryLevel())) {
                                if (firstLastCategory.getResult().get(key).size() != 0) {
                                    commonLib.fail(key + " level does not fetch correctly as per config.", false);
                                }
                            }
                        }
                    }
                    for (CategoryHierarchy level : lastLevelCategories) {
                        log.info(level.getCategoryName() + ": " + level.getId());
                        ids.put(level.getCategoryName().toLowerCase().trim(), level.getId());
                    }
                } else {
                    restUtils.printFailLog("First and Last Categories can not be empty.");
                }

                assertCheck.append(actions.assertEqualStringStatusCode(firstLastCategory.getStatus(), "SUCCESS", "API Status Matched", "API Status not Matched and is - " + firstLastCategory.getStatus()));
                assertCheck.append(actions.assertEqualIntType(firstLastCategory.getStatusCode(), 200, "Status Code Matched", "Status Code Not Matched"));
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - firstLastCategoryTest " + e.getMessage(), false);
        }
    }

    @Test(priority = 2, description = "Validate API Response When API Request is Invalid", groups = {"RegressionTest"})
    public void firstLastCategoryInvalidTokenTest() {
        try {
            selUtils.addTestcaseDescription("Validate /v1/firstlast/categories API With Invalid Request", "Validate /v1/firstlast/categories API With Invalid Request");
            CategoryHierarchyRequest firstLastCategory = api.firstLastCategoryHierarchyTest(restUtils.invalidToken());
            assertCheck.append(actions.assertEqualStringType(firstLastCategory.getStatus(), "401", "Status Matched", "Status Not Matched and is - " + firstLastCategory.getStatus()));
            assertCheck.append(actions.assertEqualStringType(firstLastCategory.getMessage(), config.getProperty("unauthorized"), "API Response Message as expected", "API Response Message as not expected and is - " + firstLastCategory.getMessage()));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Caught exception in Testcase - firstLastCategoryInvalidTokenTest " + e.getMessage(), false);
        }
    }
}
