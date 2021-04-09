package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.commonutils.dataproviders.TestDatabean;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.driver.Driver;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class BackendAgentLoginTest extends Driver {

    @BeforeMethod
    public void checkExecution() {
        SoftAssert softAssert = new SoftAssert();
        if (!continueExecutionBA) {
            softAssert.fail("Terminate Execution as Backend Agent not able to login into portal or Role does not assign to user. Please do needful.");
        }
        softAssert.assertAll();
    }

    @DataProviders.User(UserType = "BA")
    @Test(priority = 1, description = "Logging IN ", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void loggingIN(TestDatabean data) throws InterruptedException {
        ExtentTestManager.startTest("Backend Agent Login Into Portal", "Logging Into Portal with AUUID :  " + data.getLoginAUUID());
        SoftAssert softAssert = new SoftAssert();
        pages.getLoginPage().openBaseURL(config.getProperty(Env + "-baseurl"));
        softAssert.assertEquals(driver.getCurrentUrl(), config.getProperty(Env + "-baseurl"), "URl isn't as expected");
        pages.getLoginPage().waitTillLoaderGetsRemoved();
        pages.getLoginPage().enterAUUID(data.getLoginAUUID());//*[@id="mat-input-7"]
        pages.getLoginPage().clickOnSubmitBtn();
        pages.getLoginPage().enterPassword(PassUtils.decodePassword(data.getPassword()));
        softAssert.assertTrue(pages.getLoginPage().checkLoginButton(), "Login Button is not enabled even after entering Passowrd");
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnVisibleButton();
        pages.getLoginPage().clickOnLogin();
        Thread.sleep(20000); // wait for 20 Seconds for Dashboard page In case of slow Network slow
        if (pages.getSideMenu().isSideMenuVisible()) {
            pages.getSideMenu().clickOnSideMenu();
            if (!pages.getSideMenu().isAgentDashboard()) {
                continueExecutionBA = false;
                softAssert.fail("Agent Dashboard does not Assign to User Visible.Please Assign Role to user.");
            } else {
                continueExecutionBA = true;
            }
            pages.getSideMenu().clickOnSideMenu();
        } else {
            continueExecutionBA = false;
            softAssert.fail("Agent Dashboard does Open with user.Check for the ScreenShot.");
        }
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Backend Agent Login into Queue", dependsOnMethods = "loggingIN")
    public void agentQueueLogin() {
        ExtentTestManager.startTest("Backend Agent Login into Queue", "Backend Agent Login into Queue");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenu().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getSideMenu().isSideMenuVisible());
        pages.getSideMenu().clickOnSideMenu();
        pages.getSideMenu().clickOnName();
        pages.getSideMenu().openBackendAgentDashboard();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getAgentLoginPage().isQueueLoginPage());
        softAssert.assertTrue(pages.getAgentLoginPage().checkSubmitButton());
        pages.getAgentLoginPage().clickSelectQueue();
        pages.getAgentLoginPage().selectAllQueue();
        pages.getAgentLoginPage().clickSubmitBtn();
        pages.getAgentLoginPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("backendAgentTicketListPage"), "Backend Agent Does not Redirect to Ticket List Page");
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentQueueLogin", description = "Ticket Search ")
    public void validateTicket() {
        ExtentTestManager.startTest("Backend Agent Validate Ticket List Page", "Validate the Backend Agent View Ticket List page");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getSupervisorTicketList().isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
        softAssert.assertTrue(pages.getSupervisorTicketList().isWorkGroupName(), "Ticket Meta Data Does Not  Have Workgroup");
        softAssert.assertTrue(pages.getSupervisorTicketList().isPrioritylabel(), "Ticket Meta Data  Does Not  Have Priority");
        softAssert.assertTrue(pages.getSupervisorTicketList().isStateLabel(), "Ticket Meta Data Does Not  Have State");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
        softAssert.assertTrue(pages.getSupervisorTicketList().isQueueLabel(), "Ticket Meta Data Have Does Not Queue");
        softAssert.assertTrue(pages.getSupervisorTicketList().isIssueLabel(), "Ticket Meta Data Have Does Not Issue");
        softAssert.assertTrue(pages.getSupervisorTicketList().isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
        softAssert.assertTrue(pages.getSupervisorTicketList().isCodeLabel(), "Ticket Meta Data Does Not Have Code");
        softAssert.assertFalse(pages.getSupervisorTicketList().getMSISDN().isEmpty(), "MSISDN Can not be empty");
        softAssert.assertAll();
    }
}
