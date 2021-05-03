package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SupervisorGlobalSearchTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (continueExecutionFA) {
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, true, "Proceeding for test case as user able to login over portal", "Skipping tests because user not able to login into portal or Role does not assign to user"));
        } else {
            commonLib.skip("Skipping tests because user not able to login into portal or Role does not assign to user");
            assertCheck.append(actions.assertEqual_boolean(continueExecutionFA, false, "Skipping tests because user not able to login into portal or Role does not assign to user"));
            throw new SkipException("Skipping tests because user not able to login into portal or Role does not assign to user");
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 1, description = "Supervisor Dashboard Login ")
    public void openSupervisorDashboard() {
        selUtils.addTestcaseDescription("Open Supervisor Dashboard", "description");
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        pages.getSideMenuPage().clickOnSideMenu();
        pages.getSideMenuPage().clickOnUserName();
        pages.getSideMenuPage().openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        pages.getSideMenuPage().waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Verify Global Search By Valid Ticket Id")
    public void globalSearchTestByTicket() {
        selUtils.addTestcaseDescription("Verify Global Search By Valid Ticket Id", "description");
        SoftAssert softAssert = new SoftAssert();
        try {
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            String ticketId = pages.getSupervisorTicketList().getTicketIdvalue();
            pages.getSupervisorTicketList().writeTicketId(ticketId);
            pages.getSupervisorTicketList().clickSearchBtn();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            softAssert.assertEquals(pages.getSupervisorTicketList().getTicketIdvalue(), ticketId, "Search Ticket does not found with Ticket Number: " + ticketId);
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("No Ticket Id Found.");
        }
        pages.getSupervisorTicketList().clearInputBox();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Verify Global Search By Invalid Ticket Id")
    public void globalSearchTestByInvalidTicket() {
        selUtils.addTestcaseDescription("Verify Global Search By Invalid Ticket Id", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        String ticketId = "987654321012";
        pages.getSupervisorTicketList().writeTicketId(ticketId);
        pages.getSupervisorTicketList().clickSearchBtn();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        softAssert.assertTrue(pages.getSupervisorTicketList().noTicketFound(), "No Result found Page does not display for Ticket Number: " + ticketId);
        pages.getSupervisorTicketList().clearInputBox();
        softAssert.assertAll();
    }

    @DataProviders.User(userType = "NFTR")
    @Test(priority = 4, description = "Verify Global Search By Global Search Option")
    public void globalSearchTestBy() {
        selUtils.addTestcaseDescription("Verify Global Search By Global Search Option", "description");
        SoftAssert softAssert = new SoftAssert();
        pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
        try {
            String msisdn = pages.getSupervisorTicketList().getMSISDN();
            pages.getSupervisorTicketList().clickTicketOption();
            pages.getSupervisorTicketList().clickSearchOptionByTextNoIgnoreCase(config.getProperty("msisdnOption"));
            pages.getSupervisorTicketList().writeTicketId(msisdn);
            pages.getSupervisorTicketList().clickSearchBtn();
            pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
            softAssert.assertEquals(pages.getSupervisorTicketList().getMSISDN(), msisdn, "Ticket does not found By searched MSISDN.");
        } catch (NoSuchElementException | TimeoutException e) {
            softAssert.fail("Not able to validate Global search by Global Search Option.");
        }
        pages.getSupervisorTicketList().clearInputBox();
        softAssert.assertAll();
    }
}
