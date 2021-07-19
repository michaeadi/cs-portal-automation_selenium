package com.airtel.cs.ui.backendAgent;

import com.airtel.cs.api.RequestSource;
import com.airtel.cs.common.actions.BaseActions;
import com.airtel.cs.commonutils.PassUtils;
import com.airtel.cs.commonutils.applicationutils.constants.ApplicationConstants;
import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
import com.airtel.cs.driver.Driver;
import com.airtel.cs.model.response.ticketlist.Ticket;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.TimeoutException;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BackendAgentLoginTest extends Driver {

    private final BaseActions actions = new BaseActions();
    RequestSource api = new RequestSource();

    @BeforeMethod(groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void checkExecution() {
        if (!continueExecutionBA) {
            commonLib.skip("Skipping tests because user NOT able to login Over Portal");
            throw new SkipException("Skipping tests because user NOT able to login Over Portal");
        }
    }

    @Test(priority = 1, groups = {"SanityTest", "RegressionTest", "ProdTest"})
    public void testBALoginInPortal() {
        try {
            selUtils.addTestcaseDescription("Logging Into Portal with Backend user credentials, Validating opened url,validating login button is getting enabled,Validating dashboard page opened successfully or not?", "description");
            loginAUUID = constants.getValue(CommonConstants.BA_USER_AUUID);
            final String value = constants.getValue(ApplicationConstants.DOMAIN_URL);
            pages.getLoginPage().openBaseURL(value);
            assertCheck.append(actions.assertEqual_stringType(driver.getCurrentUrl(), value, "Correct URL Opened", "URl isn't as expected"));
            pages.getLoginPage().enterAUUID(loginAUUID);
            pages.getLoginPage().clickOnSubmitBtn();
            pages.getLoginPage().enterPassword(PassUtils.decodePassword(constants.getValue(CommonConstants.BA_USER_PASSWORD)));
            assertCheck.append(actions.assertEqual_boolean(pages.getLoginPage().checkLoginButton(), true, "Login Button is Enabled", "Login Button is NOT enabled"));
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnVisibleButton();
            pages.getLoginPage().clickOnLogin();
            final boolean isGrowlVisible = pages.getGrowl().checkIsGrowlVisible();
            if (isGrowlVisible) {
                commonLib.fail("Growl Message:- " + pages.getGrowl().getToastContent(), true);
                continueExecutionBA = false;
            } else {
                assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isBEAgentDashboard(), true, "Customer Dashboard Page Loaded Successfully", "Customer Dashboard page NOT Loaded"));
                actions.assertAllFoundFailedAssert(assertCheck);
            }
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (Exception e) {
            continueExecutionBA = false;
            commonLib.fail("Exception in Method - testBALoginInPortal" + e.fillInStackTrace(), true);
        }
    }

    @Test(priority = 2, groups = {"SanityTest", "RegressionTest", "ProdTest"}, dependsOnMethods = "testBALoginInPortal")
    public void agentQueueLogin() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Login into Queue", "description");
            commonLib.info("Opening URL");
            assertCheck.append(actions.assertEqual_boolean(pages.getSideMenuPage().isSideMenuVisible(), true, "Side Menu Visible Successfully", "Side Menu NOT Visible"));
            pages.getSideMenuPage().clickOnSideMenu();
            pages.getSideMenuPage().openBackendAgentDashboard();
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().isQueueLoginPage(), true, "Queue Login Page is Visible", "Queue Login Page is NOT Visible"));
            assertCheck.append(actions.assertEqual_boolean(pages.getAgentLoginPage().checkSubmitButton(), true, "Submit button for login is Enabled", "Submit button for login is NOT Enabled"));
            pages.getAgentLoginPage().clickSelectQueue();
            pages.getAgentLoginPage().selectAllQueue();
            pages.getAgentLoginPage().clickSubmitBtn();
            assertCheck.append(actions.assertEqual_stringType(driver.getTitle(), constants.getValue(CommonConstants.BACKEND_AGENT_TICKET_LIST_PAGE), "Backend Agent Successfully Redirect to Ticket List Page", "Backend Agent Does not Redirect to Ticket List Page"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - agentQueueLogin", true);
        }
    }

    @Test(priority = 3, groups = {"SanityTest", "RegressionTest"}, dependsOnMethods = "testBALoginInPortal")
    public void validateTicket() {
        try {
            selUtils.addTestcaseDescription("Backend Agent Validate Ticket List Page", "description");
            Ticket ticketPOJO = api.ticketMetaDataTest(pages.getSupervisorTicketList().getTicketIdValue());
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isTicketIdLabel(), true, "Ticket Meta Data Have Ticket Id", "Ticket Meta Data Does Not Have Ticket Id"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isWorkGroupName(), true, "Ticket Meta Data Have Workgroup", "Ticket Meta Data Does Not  Have Workgroup"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isPrioritylabel(), true, "Ticket Meta Data Have Priority", "Ticket Meta Data  Does Not  Have Priority"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isStateLabel(), true, "Ticket Meta Data Have State", "Ticket Meta Data Does Not  Have State"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCreationDateLabel(), true, "Ticket Meta Data Have Creation Date", "Ticket Meta Data Does Not Have Creation Date"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCreatedByLabel(), true, "Ticket Meta Data Have Created By", "Ticket Meta Data Does Not Have Created By"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isQueueLabel(), true, "Ticket Meta Data Have Queue", "Ticket Meta Data Does Not Have Queue"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isIssueLabel(), true, "Ticket Meta Data Have Issue", "Ticket Meta Data Does Not Have Issue"));
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isIssueTypeLabel(), true, "Ticket Meta Data Have Issue Type", "Ticket Meta Data Does Not Have Issue Type"));
            if(ticketPOJO.getResult().getCategoryLevel().size()>3) {
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSubTypeLabel(), true, "Ticket Meta Data Have Issue Sub Type", "Ticket Meta Data Does Not Have Issue Sub Type"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSubSubTypeLabel(), true, "Ticket Meta Data Have Issue Sub Sub Type", "Ticket Meta Data Does Not Have Issue Sub Sub Type"));
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isCodeLabel(), true, "Ticket Meta Data Have Code", "Ticket Meta Data Does Not Have Code"));
            }else{
                assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().isSubTypeLabel(), true, "Ticket Meta Data Have Code", "Ticket Meta Data Does Not Have Code"));
            }
            assertCheck.append(actions.assertEqual_boolean(pages.getSupervisorTicketList().getMSISDN().isEmpty(), false, "MSISDN Can be empty", "MSISDN Can not be empty"));
            actions.assertAllFoundFailedAssert(assertCheck);
        } catch (NotFoundException | TimeoutException | ElementClickInterceptedException e) {
            commonLib.fail("Exception in Method - validateTicket", true);
        }
    }
}
