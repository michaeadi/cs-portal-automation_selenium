package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.pagerepository.pagemethods.SideMenuPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.lang.reflect.Method;
import java.util.List;

public class SupervisorLoginTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBS) {
            softAssert.fail("Terminate Execution as Backend Supervisor user not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User()
    @Test(priority = 1, description = "Logging IN", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void loggingIN(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Logging Into Portal", "Logging Into Portal with AUUID :  " + data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        pages.getLoginPage().openBaseURL(config.getProperty(evnName + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(evnName + "-baseurl"), "URl isn't as expected");
        pages.getLoginPage().enterAUUID(data.getLoginAUUID());//*[@id="mat-input-7"]
        pages.getLoginPage().clickOnSubmitBtn();
        pages.getLoginPage().enterPassword(PassUtils.decodePassword(data.getPassword()));
        softAssert.assertTrue(pages.getLoginPage().checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnLogin();
        SideMenuPage sideMenu = new SideMenuPage(driver);
        Thread.sleep(20000); // wait for 20 Seconds for Dashboard page In case of slow Network slow
        if (sideMenu.isSideMenuVisible()) {
            sideMenu.clickOnSideMenu();
            if (!sideMenu.isCustomerServicesVisible()) {
                continueExecutionBS = false;
                softAssert.fail("Backend Supervisor Dashboard does not Assign to User.Please Assign Role to user.");
            } else {
                continueExecutionBS = true;
            }
            sideMenu.clickOnSideMenu();
        } else {
            continueExecutionBS = false;
            softAssert.fail("Backend Supervisor Dashboard does Open with user.Check for the ScreenShot.");
        }
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard(Method method) {
        ExtentTestManager.startTest("Open Supervisor Dashboard", "Open Supervisor Dashboard");
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify there are Searchable fields options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN")
    public void validateTicketSearchOptions() {
        ExtentTestManager.startTest("Validate Search Ticket Option", "Verify there are 2 options displayed to select from in the Search Dropdown : 1) Ticket Id & 2) MSISDN");
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
        ExtentTestManager.startTest("Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket) ", "Validate Supervisor ticket tabs(All Tickets & My Assigned Ticket)");
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(pages.getSupervisorTicketList().isMyAssignedTicketTab(), "My Assigned Tickets Tab does not displayed correctly.");
        softAssert.assertTrue(pages.getSupervisorTicketList().isAllTicketTab(), "ALL Tickets Tab does not displayed correctly.");
        softAssert.assertAll();
    }
}