package com.airtel.cs.ui.frontendagent.hbb;

import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.commonutils.dataproviders.dataproviders.DataProviders;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.hbb.HbbUserDetailsResponse;
import com.airtel.cs.model.response.smshistory.SMSHistory;
import com.airtel.cs.model.response.smshistory.SMSHistoryList;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;

public class HbbCreateInteractionTest extends Driver {

    String customerNumber = null;

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void checkExecution() {
        if (!continueExecutionFA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest", "SmokeTest"})
    public void openCustomerInteraction() {
        try {
            selUtils.addTestcaseDescription("Open Customer Profile Page with valid MSISDN, Validate Customer Profile Page Loaded or not", "description");
            customerNumber = constants.getValue(ApplicationConstants.HBB_CUSTOMER_MSISDN);
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openCustomerInteractionPage();
            pages.getMsisdnSearchPage().enterNumber(customerNumber);
            pages.getMsisdnSearchPage().clickOnSearch();
            final boolean pageLoaded = pages.getCustomerProfilePage().isCustomerProfilePageLoaded();
            assertCheck.append(actions.assertEqualBoolean(pageLoaded, true, "Customer Profile Page Loaded Successfully", "Customer Profile Page NOT Loaded"));
            if (!pageLoaded) continueExecutionFA = false;
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openCustomerInteraction" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void createInteractionSMSTest() {
        try {
            selUtils.addTestcaseDescription("Create interaction , check its entry in View History > SMS Tab", "description");
            pages.getHbbProfilePage().createNFTRInteraction(customerNumber);
            pages.getCustomerProfilePage().goToViewHistory();
            pages.getViewHistory().clickOnMessageHistory();
            SMSHistory historyResponse = api.smsHistoryTest(customerNumber);
            assertCheck.append(actions.matchUiAndAPIResponse(customerNumber, historyResponse.getResult().get(0).getAlternateNumber(),
                    "Ticket Creation SMS is sent on Alternate Number", "Ticket Creation SMS is not sent on Alternate Number"));
        } catch (Exception e) {
            commonLib.fail("Exception in Method - createInteractionSMSTest" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }
    @Test(priority = 3, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void openSupervisorDashboard() {
        try {
            selUtils.addTestcaseDescription("Open Supervisor Dashboard for closing ticket", "description");
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().clickOnUserName();
            pages.getSideMenuPage().openSupervisorDashboard();
            assertCheck.append(actions.assertEqualStringType(driver.getTitle(), constants.getValue(CommonConstants.SUPERVISOR_TICKET_LIST_PAGE_TITLE), "Agent redirect to ticket list page as expected", "Agent does not redirect to ticket list page as expected"));
            pages.getSupervisorTicketList().changeTicketTypeToClosed();
        } catch (Exception e) {
            commonLib.fail("Exception in Method - openSupervisorDashboard" + e.fillInStackTrace(), true);
        }
        actions.assertAllFoundFailedAssert(assertCheck);
    }

}
