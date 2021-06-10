package com.airtel.cs.ui.backendSupervisor;

import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class SupervisorGlobalSearchTest extends Driver {

    private final BaseActions actions = new BaseActions();

    @BeforeMethod
    public void checkExecution() {
        if (!continueExecutionBS) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1)
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard , Validate agent redirect to ticket List Page", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), config.getProperty("supervisorTicketListPage"), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, description = "Verify Global Search By Valid Ticket Id")
    public void globalSearchTestByTicket() {
        try {
            selUtils.addTestcaseDescription("Verify Global Search By Valid Ticket Id,Search By ticket id", "description");
            try {
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                String ticketId = pages.getSupervisorTicketList().getTicketIdValue();
                pages.getSupervisorTicketList().writeTicketId(ticketId);
                pages.getSupervisorTicketList().clickSearchBtn();
                pages.getSupervisorTicketList().waitTillLoaderGetsRemoved();
                assertCheck.append(actions.assertEqual_stringType(pages.getSupervisorTicketList().getTicketIdValue(), ticketId, "Search Ticket found.", "Search Ticket does not found with Ticket Number: " + ticketId));
            } catch (NoSuchElementException | TimeoutException e) {
                commonLib.fail("No Ticket Id Found.", true);
            }
            pages.getSupervisorTicketList().clearInputBox();
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            commonLib.fail("Exception in Method - globalSearchTestByTicket" + e.fillInStackTrace(), true);
        }
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
