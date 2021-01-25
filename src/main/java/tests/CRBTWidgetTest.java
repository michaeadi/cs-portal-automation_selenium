package tests;

import API.APITest;
import POJO.CRBT.ActivateRingtone;
import POJO.CRBT.Top20Ringtone;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.CRBTWidgetPOM;
import pages.SideMenuPOM;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;

public class CRBTWidgetTest extends BaseTest {

    String customerNumber = null;
    APITest api = new APITest();

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search for Customer Interactions: " + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerNumber = Data.getCustomerNumber();
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @DataProviders.Table(Name = "Search Tune Tab")
    @Test(priority = 2, description = "Validate CRBT Widget Displayed Correctly", dataProvider = "HeaderData", dataProviderClass = DataProviders.class)
    public void validateMyTuneTab(HeaderDataBean data) {
        ExtentTestManager.startTest("Validate CRBT Widget Displayed Correctly", "Validate CRBT Widget Displayed Correctly");
        SoftAssert softAssert = new SoftAssert();
        CRBTWidgetPOM crbtWidget = new CRBTWidgetPOM(driver);
        Assert.assertTrue(crbtWidget.isCRBTWidgetDisplay(), "CRBT Widget Does not display");
        ActivateRingtone ringtoneAPI = api.activateRingtone(customerNumber);

        if (ringtoneAPI.getResult().getIsHelloTunesSubscribed() && Integer.parseInt(ringtoneAPI.getStatusCode())==200) {
            /*
             * Assertion In case of ringtone activate
             * */
        } else {
            softAssert.assertTrue(crbtWidget.isNoResultMessage(), "No Result message does not in case of no ringtone subscribed.");
            softAssert.assertTrue(crbtWidget.isNoResultImg(), "No Result Image does not in case of no ringtone subscribed.");
        }
        crbtWidget.clickSearchTuneTab();
        Top20Ringtone searchTune = api.ringtoneDetailTest(customerNumber, "namedTune", "h");
        crbtWidget.searchKeyword("h");
        crbtWidget.clickSearchBtn();
        crbtWidget.waitTillTimeLineGetsRemoved();
        try{
            crbtWidget.clickSearchOption();
            softAssert.assertEquals(crbtWidget.getOption1().trim().toLowerCase(),config.getProperty("searchByName").trim().toLowerCase(),"Search By option does not same as expected.");
            softAssert.assertEquals(crbtWidget.getOption2().trim().toLowerCase(),config.getProperty("searchByTitle").trim().toLowerCase(),"Search By option does not same as expected.");
        }catch (NoSuchElementException | TimeoutException e){
            softAssert.fail("Not able to search option: "+e.fillInStackTrace());
        }
        crbtWidget.clickOutside();
        if (!searchTune.getStatusCode().equalsIgnoreCase("200")) {
            softAssert.fail("API Response "+searchTune.getMessage());
            softAssert.assertTrue(crbtWidget.isWidgetError(),"Widget Error does not display as api response is not 200.");
        } else if (searchTune.getTotalCount() > 0) {
            softAssert.assertEquals(crbtWidget.getSearchHeader(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-1");
            softAssert.assertEquals(crbtWidget.getSearchHeader(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-2");
            softAssert.assertEquals(crbtWidget.getSearchHeader(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-3");
//            softAssert.assertEquals(crbtWidget.getSearchHeader(4).trim().toLowerCase(), data.getRow4().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-4");
            int size= Math.min(searchTune.getTotalCount(), 5);
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(crbtWidget.getValueSearch(i+1,1).trim().toLowerCase(),(searchTune.getResult().get(i).getName()+searchTune.getResult().get(i).getSinger()).toLowerCase().trim(),"Name is not same as expected in API response");
                softAssert.assertEquals(crbtWidget.getValueSearch(i+1,2).trim(),searchTune.getResult().get(i).getRenewalAmount().trim(),"Renewal Amount is not same as expected in API response");
                softAssert.assertEquals(crbtWidget.getValueSearch(i+1,3).trim(),searchTune.getResult().get(i).getPeriod().trim(),"Period is not same as expected in API response");
            }
        } else {
            softAssert.assertTrue(crbtWidget.isNoResultMessage(), "No Result message does not in case of no ringtone found with search keyword.");
            softAssert.assertTrue(crbtWidget.isNoResultImg(), "No Result Image does not in case of no ringtone found with search keyword.");
        }
        crbtWidget.clickTop20Tab();
        Top20Ringtone top20Tune = api.ringtoneDetailTest(customerNumber, "topTwenty", null);
        crbtWidget.waitTillTimeLineGetsRemoved();

        if (!top20Tune.getStatusCode().equalsIgnoreCase("200")) {
            softAssert.fail("API Response "+top20Tune.getMessage());
            softAssert.assertTrue(crbtWidget.isWidgetError(),"Widget Error does not display as api response is not 200.");
        } else if (top20Tune.getTotalCount() > 0) {
            softAssert.assertEquals(crbtWidget.getTop20Header(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-1");
            softAssert.assertEquals(crbtWidget.getTop20Header(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-2");
            softAssert.assertEquals(crbtWidget.getTop20Header(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-3");
//            softAssert.assertEquals(crbtWidget.getTop20Header(4).trim().toLowerCase(), data.getRow4().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-4");
            int size= Math.min(top20Tune.getTotalCount(), 5);
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(crbtWidget.getValueTop20(i+1,1).trim().toLowerCase(),(top20Tune.getResult().get(i).getName()+top20Tune.getResult().get(i).getSinger()).toLowerCase().trim(),"Name is not same as expected in API response");
                softAssert.assertEquals(crbtWidget.getValueTop20(i+1,2).trim(),top20Tune.getResult().get(i).getRenewalAmount().trim(),"Renewal Amount is not same as expected in API response");
                softAssert.assertEquals(crbtWidget.getValueTop20(i+1,3).trim(),top20Tune.getResult().get(i).getPeriod().trim(),"Period is not same as expected in API response");
            }
        } else {
            softAssert.assertTrue(crbtWidget.isNoResultMessage(), "No Result message does not in case of no ringtone found with search keyword.");
            softAssert.assertTrue(crbtWidget.isNoResultImg(), "No Result Image does not in case of no ringtone found with search keyword.");
        }

    }
}
