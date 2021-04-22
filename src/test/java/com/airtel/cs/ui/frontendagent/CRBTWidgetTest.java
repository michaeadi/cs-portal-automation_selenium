package com.airtel.cs.ui.frontendagent;

import com.airtel.cs.api.APIEndPoints;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.HeaderDataBean;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pojo.crbt.ActivateRingtone;
import com.airtel.cs.pojo.crbt.Top20Ringtone;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CRBTWidgetTest extends Driver {

    String customerNumber = null;
    APIEndPoints api = new APIEndPoints();

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionFA || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean data) {
        selUtils.addTestcaseDescription("Validating the Search for Customer Interactions: " + data.getCustomerNumber(), "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openCustomerInteractionPage();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        pages.getMsisdnSearchPage().enterNumber(data.getCustomerNumber());
        customerNumber = data.getCustomerNumber();
        pages.getMsisdnSearchPage().clickOnSearch();
        softAssert.assertTrue(pages.getCustomerProfilePage().isPageLoaded());
        softAssert.assertAll();
    }

    @DataProviders.Table(name = "Search Tune Tab")
    @Test(priority = 2, description = "Validate CRBT Widget Displayed Correctly", dataProvider = "HeaderData", dataProviderClass = DataProviders.class, dependsOnMethods = "openCustomerInteraction")
    public void validateMyTuneTab(HeaderDataBean data) {
        selUtils.addTestcaseDescription("Validate CRBT Widget Displayed Correctly", "description");
        SoftAssert softAssert = new SoftAssert();
        Assert.assertTrue(pages.getCrbtWidgetPage().isCRBTWidgetDisplay(), "CRBT Widget Does not display");
        ActivateRingtone ringtoneAPI = api.activateRingtone(customerNumber);
        boolean status = ringtoneAPI.getResult().getIsHelloTunesSubscribed();
        if (status && Integer.parseInt(ringtoneAPI.getStatusCode()) == 200) {
            /*
             * Assertion In case of ringtone activate
             * */
        } else {
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultMessage(), "No Result message does not in case of no ringtone subscribed.");
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultImg(), "No Result Image does not in case of no ringtone subscribed.");
        }
        pages.getCrbtWidgetPage().clickSearchTuneTab();
        Top20Ringtone searchTune = api.ringtoneDetailTest(customerNumber, "namedTune", "h");
        pages.getCrbtWidgetPage().searchKeyword("h");
        pages.getCrbtWidgetPage().clickSearchBtn();
        pages.getCrbtWidgetPage().waitTillTimeLineGetsRemoved();
        try {
            pages.getCrbtWidgetPage().clickSearchOption();
            softAssert.assertEquals(pages.getCrbtWidgetPage().getOption1().trim().toLowerCase(), config.getProperty("searchByName").trim().toLowerCase(), "Search By option does not same as expected.");
            softAssert.assertEquals(pages.getCrbtWidgetPage().getOption2().trim().toLowerCase(), config.getProperty("searchByTitle").trim().toLowerCase(), "Search By option does not same as expected.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to search option: " + e.fillInStackTrace());
        }
        pages.getCrbtWidgetPage().clickOutside();
        if (!searchTune.getStatusCode().equalsIgnoreCase("200")) {
            softAssert.fail("API Response " + searchTune.getMessage());
            softAssert.assertTrue(pages.getCrbtWidgetPage().isWidgetError(), "Widget Error does not display as api response is not 200.");
        } else if (searchTune.getTotalCount() > 0) {
            softAssert.assertEquals(pages.getCrbtWidgetPage().getSearchHeader(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-1");
            softAssert.assertEquals(pages.getCrbtWidgetPage().getSearchHeader(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-2");
            softAssert.assertEquals(pages.getCrbtWidgetPage().getSearchHeader(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-3");
            int size = Math.min(searchTune.getTotalCount(), 5);
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueSearch(i + 1, 1).trim().toLowerCase(), (searchTune.getResult().get(i).getName() + searchTune.getResult().get(i).getSinger()).toLowerCase().trim(), "Name is not same as expected in com.airtel.cs.API response");
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueSearch(i + 1, 2).trim(), searchTune.getResult().get(i).getRenewalAmount().trim(), "Renewal Amount is not same as expected in com.airtel.cs.API response");
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueSearch(i + 1, 3).trim(), searchTune.getResult().get(i).getPeriod().trim(), "Period is not same as expected in com.airtel.cs.API response");
            }
        } else {
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultMessage(), "No Result message does not in case of no ringtone found with search keyword.");
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultImg(), "No Result Image does not in case of no ringtone found with search keyword.");
        }
        pages.getCrbtWidgetPage().clickTop20Tab();
        Top20Ringtone top20Tune = api.ringtoneDetailTest(customerNumber, "topTwenty", null);
        pages.getCrbtWidgetPage().waitTillTimeLineGetsRemoved();

        if (!top20Tune.getStatusCode().equalsIgnoreCase("200")) {
            softAssert.fail("com.airtel.cs.API Response " + top20Tune.getMessage());
            softAssert.assertTrue(pages.getCrbtWidgetPage().isWidgetError(), "Widget Error does not display as api response is not 200.");
        } else if (top20Tune.getTotalCount() > 0) {
            softAssert.assertEquals(pages.getCrbtWidgetPage().getTop20Header(1).trim().toLowerCase(), data.getRow1().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-1");
            softAssert.assertEquals(pages.getCrbtWidgetPage().getTop20Header(2).trim().toLowerCase(), data.getRow2().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-2");
            softAssert.assertEquals(pages.getCrbtWidgetPage().getTop20Header(3).trim().toLowerCase(), data.getRow3().trim().toLowerCase(), "Header name for Search tab not same as expected at POS-3");
            int size = Math.min(top20Tune.getTotalCount(), 5);
            for (int i = 0; i < size; i++) {
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueTop20(i + 1, 1).trim().toLowerCase(), (top20Tune.getResult().get(i).getName() + top20Tune.getResult().get(i).getSinger()).toLowerCase().trim(), "Name is not same as expected in com.airtel.cs.API response");
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueTop20(i + 1, 2).trim(), top20Tune.getResult().get(i).getRenewalAmount().trim(), "Renewal Amount is not same as expected in com.airtel.cs.API response");
                softAssert.assertEquals(pages.getCrbtWidgetPage().getValueTop20(i + 1, 3).trim(), top20Tune.getResult().get(i).getPeriod().trim(), "Period is not same as expected in com.airtel.cs.API response");
            }
        } else {
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultMessage(), "No Result message does not in case of no ringtone found with search keyword.");
            softAssert.assertTrue(pages.getCrbtWidgetPage().isNoResultImg(), "No Result Image does not in case of no ringtone found with search keyword.");
        }
        softAssert.assertAll();
    }
}
