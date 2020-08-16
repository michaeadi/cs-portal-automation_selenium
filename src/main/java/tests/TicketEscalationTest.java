package tests;

import Utils.DataProviders.DataProviders;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;
import java.util.List;

public class TicketEscalationTest extends BaseTest {

    @Test(priority = 1, description = "Supervisor SKIP Login ")
    public void agentSkipQueueLogin(Method method){
        ExtentTestManager.startTest("Supervisor SKIP Queue Login Test", "Supervisor SKIP Queue Login Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(),"Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(),"Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(),"Checking Queue Login Page have Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        AgentLoginPagePOM.waitTillLoaderGetsRemoved();
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Validate Ticket Escalation Symbol", dataProviderClass = DataProviders.class)
    public void ticketEscalation(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate Ticket Escalation", "Verify that ticket Escalation Symbol Display");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.changeTicketTypeToOpen();
        ticketListPage.waitTillLoaderGetsRemoved();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.OpenEscalationFilter();
        filterTab.selectAllLevel1();
        filterTab.selectAllLevel2();
        filterTab.selectAllLevel3();
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try{
            if(!ticketListPage.noTicketFound()){
                for(int i=1;i<=ticketListPage.getListSize();i++){
                    String symbol=ticketListPage.getSymbol(i);
                    softAssert.assertTrue(symbol.equalsIgnoreCase("!") || symbol.equalsIgnoreCase("!!") || symbol.equalsIgnoreCase("!!!"),"Ticket Symbol not displayed correctly");
                }
            }else{
                System.out.println("No Ticket Found for Selected Filter");
                ExtentTestManager.getTest().log(LogStatus.WARNING,"No Ticket Found for Selected Filter");
            }

        } catch (Exception e) {
            softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
            ExtentTestManager.getTest().log(LogStatus.ERROR,e.fillInStackTrace());
            e.printStackTrace();
        }
        ticketListPage.resetFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, dependsOnMethods = "agentSkipQueueLogin", description = "Validate the Escalation of Ticket Before the SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationBeforeSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket Before the SLA Expiry", "Validate the Escalation of Ticket Before the SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.OpenEscalationFilter();
        filterTab.selectAllLevel1();
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try{
            if(!ticketListPage.noTicketFound()){
                for(int i=1;i<=ticketListPage.getListSize();i++){
                    String symbol=ticketListPage.getSymbol(i);
                    softAssert.assertTrue(symbol.equalsIgnoreCase("!") ,"Ticket Symbol not displayed correctly");
                }
            }else{
                ExtentTestManager.getTest().log(LogStatus.WARNING,"No Ticket Found for Selected Filter");
            }

        } catch (Exception e) {
            softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
            ExtentTestManager.getTest().log(LogStatus.ERROR,e.fillInStackTrace());
            e.printStackTrace();
        }
        ticketListPage.resetFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 4, dependsOnMethods = "agentSkipQueueLogin", description = "Validate the Escalation of Ticket after SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationAfterSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket after SLA Expiry", "Validate the Escalation of Ticket after SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.OpenEscalationFilter();
        filterTab.selectAllLevel3();
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try{
            if(!ticketListPage.noTicketFound()){
                for(int i=1;i<=ticketListPage.getListSize();i++){
                    String symbol=ticketListPage.getSymbol(i);
                    softAssert.assertTrue(symbol.equalsIgnoreCase("!!!"),"Ticket Symbol not displayed correctly");
                }
            }else{
                ExtentTestManager.getTest().log(LogStatus.WARNING,"No Ticket Found for Selected Filter");
            }

        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
            ExtentTestManager.getTest().log(LogStatus.ERROR,e.fillInStackTrace());
        }
        ticketListPage.resetFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 5, dependsOnMethods = "agentSkipQueueLogin", description = "Validate the Escalation of Ticket on SLA Expiry", dataProviderClass = DataProviders.class)
    public void ticketEscalationOnSLA(Method method) throws InterruptedException {
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        assignToAgentPOM assignTicket = new assignToAgentPOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        ExtentTestManager.startTest("Validate the Escalation of Ticket on SLA Expiry", "Validate the Escalation of Ticket on SLA Expiry");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.OpenEscalationFilter();
        filterTab.selectAllLevel2();
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        try{
            if(!ticketListPage.noTicketFound()){
                for(int i=1;i<=ticketListPage.getListSize();i++){
                    String symbol=ticketListPage.getSymbol(i);
                    softAssert.assertTrue(symbol.equalsIgnoreCase("!!"),"Ticket Symbol not displayed correctly");
                }
            }else{
                ExtentTestManager.getTest().log(LogStatus.WARNING,"No Ticket Found for Selected Filter");
            }

        } catch (Exception e) {
            e.printStackTrace();
            softAssert.fail("Ticket Escalation Symbol on ticket not displayed correctly");
            ExtentTestManager.getTest().log(LogStatus.ERROR,e.fillInStackTrace());
        }
        ticketListPage.resetFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

}
