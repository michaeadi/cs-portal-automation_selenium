package com.airtel.cs.ui.configurationtest;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.configurationapi.ConfigurationList;
import org.testng.annotations.Test;

public class ConfigurationListTest extends Driver {
    RequestSource api=new RequestSource();
    private final String CREATE_KEY="autotest";
    private final String UPDATE_VALUE="autoUpdateValue";
    private final String CREATE_VALUE="autovalue";

    @Test(priority = 1,groups = {"SanityTest","RegressionTest","ProdTest"})
    public void getConfigurationListWithValidateReq(){
        try{
            selUtils.addTestcaseDescription("Validate get configuration API with valid request, Status Code must be 200,Result size must be equal to page size","description");
            int pageSize=10;
            Integer pageNumber=0;
            ConfigurationList configurationListTest=api.getAllConfiguration(pageSize,pageNumber);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.assertEqualIntType(configurationListTest.getResult().getResult().size(),pageSize,"Total Number of result fetched is equal to page size","Total Number of result fetched is not equal to page size"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.api.success.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - getConfigurationListWithValidateReq" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2,groups = {"RegressionTest"})
    public void getConfigurationListWithNullReq(){
        try{
            selUtils.addTestcaseDescription("Validate get configuration API with page number and page size null, Status Code must be 200,Result size must be 10 and page number must be 0","description");
            ConfigurationList configurationListTest=api.getAllConfiguration(null,null);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.assertEqualIntType(configurationListTest.getResult().getResult().size(),10,"Total Number of result fetched is equal to page size","Total Number of result fetched is not equal to page siz"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.api.success.status"),"API Status as expected","API status not same as expected"));
            assertCheck.append(actions.assertEqualIntType(configurationListTest.getResult().getPageNumber(),0,"Page number in API response equal to request page number","Page number in API response does not equal to request page number"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - getConfigurationListWithNullReq" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 3,groups = {"RegressionTest"})
    public void createConfigWithValidRequest(){
        try{
            selUtils.addTestcaseDescription("Validate Create configuration API with valid request, Status Code must be 200,Validate API message same as expected","description");
            ConfigurationList configurationListTest=api.createConfig(CREATE_KEY,CREATE_VALUE);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getMessage(),constants.getValue("create.config.key.success"),"API response message as expected","API response message not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.api.success.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - createConfigWithValidRequest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 4,groups = {"RegressionTest"})
    public void createConfigWithDuplicateRequest(){
        try{
            selUtils.addTestcaseDescription("Validate Create configuration API with duplicate request(Key already present), Status Code must be 400,Validate API message same as expected","description");
            ConfigurationList configurationListTest=api.createConfig(CREATE_KEY,CREATE_VALUE);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 400, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getMessage(),constants.getValue("create.config.key.already.present"),"API response message as expected","API response message not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.failed.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - createConfigWithDuplicateRequest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 5,groups = {"RegressionTest"})
    public void updateConfigWithValidRequest(){
        try{
            selUtils.addTestcaseDescription("Validate update configuration API with updated value, Status Code must be 200,Validate Status and API Message","description");
            ConfigurationList configurationListTest=api.updateConfig(CREATE_KEY,UPDATE_VALUE);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.api.success.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - updateConfigWithValidRequest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 6,groups = {"RegressionTest"})
    public void deleteConfigWithValidRequest(){
        try{
            selUtils.addTestcaseDescription("Validate delete configuration API with valid key(Key does present), Status Code must be 200,Validate Status and API Message","description");
            ConfigurationList configurationListTest=api.deleteConfig(CREATE_KEY);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 200, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getMessage(),constants.getValue("delete.config.success"),"API response message as expected","API response message not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.api.success.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - deleteConfigWithValidRequest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 7,groups = {"RegressionTest"})
    public void deleteConfigWithInvalidKey(){
        try{
            selUtils.addTestcaseDescription("Validate delete configuration API with invalid key(Key does not present), Status Code must be 400,Validate Status and API Message","description");
            ConfigurationList configurationListTest=api.deleteConfig(CREATE_KEY);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 400, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getMessage(),constants.getValue("delete.config.key.not.found"),"API response message as expected","API response message not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.failed.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - deleteConfigWithInvalidKey" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 8,groups = {"RegressionTest"})
    public void updateConfigWithInvalidKey(){
        try{
            selUtils.addTestcaseDescription("Validate update configuration API with invalid key(Key does not present), Status Code must be 400,Validate Status and API Message","description");
            ConfigurationList configurationListTest=api.updateConfig(CREATE_KEY,UPDATE_VALUE);
            Integer statusCode=configurationListTest.getStatusCode();
            assertCheck.append(actions.assertEqualIntType(statusCode, 400, "Get Configuration API success and status code is :" + statusCode, "Get Configuration API got failed and status code is :" + statusCode, false, true));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getMessage(),constants.getValue("delete.config.key.not.found"),"API response message as expected","API response message not same as expected"));
            assertCheck.append(actions.matchUiAndAPIResponse(configurationListTest.getStatus(),constants.getValue("cs.portal.failed.status"),"API Status as expected","API status not same as expected"));
        }catch (Exception e){
            commonLib.fail("Exception in Method - updateConfigWithInvalidKey" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
}
