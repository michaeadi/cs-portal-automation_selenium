package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.databeans.TestDatabean;
import com.airtel.cs.driver.Driver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class PRODSupervisorTicketTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS || !continueExecutionAPI) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void loggingIN(TestDatabean data) {
        selUtils.addTestcaseDescription("Logging Into Portal with AUUID :  " + data.getLoginAUUID(), "description");
        SoftAssert softAssert = new SoftAssert();
        final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
        pages.getLoginPage().openBaseURL(value);
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(evnName + "-baseurl"), "URl isn't as expected");
        pages.getLoginPage().enterAUUID(data.getLoginAUUID());//*[@id="mat-input-7"]
        pages.getLoginPage().clickOnSubmitBtn();
        pages.getLoginPage().enterPassword(PassUtils.decodePassword(data.getPassword()));
        softAssert.assertTrue(pages.getLoginPage().checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnLogin();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        commonLib.info("Opening URL");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify there are Searchable fields options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN")
    public void validateTicketSearchOptions() {
        selUtils.addTestcaseDescription("Validate Search Ticket Option,Verify there are 2 options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN", "description");
        commonLib.info("Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().clickTicketOption();
        List<String> list = pages.getSupervisorTicketList().getListOfSearchOption();
        softAssert.assertTrue(list.contains(config.getProperty("ticketOption")), config.getProperty("ticketOption") + " option does not found in list.");
        softAssert.assertTrue(list.contains(config.getProperty("msisdnOption")), config.getProperty("msisdnOption") + " option does not found in list.");
        pages.getSupervisorTicketList().clickTicketOption();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validate Supervisor ticket tabs ALL Tickets & My Assigned Tab")
    public void validateSupervisorTabs() {
        selUtils.addTestcaseDescription("Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket) ", "description");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getSupervisorTicketList().isMyAssignedTicketTab(), "My Assigned Tickets Tab does not displayed correctly.");
        softAssert.assertTrue(pages.getSupervisorTicketList().isAllTicketTab(), "ALL Tickets Tab does not displayed correctly.");
        softAssert.assertAll();
    }
}
