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
            System.out.println("Ticket List: "+ticketListPage.noTicketFound());
            if(!ticketListPage.noTicketFound()){
                List<WebElement> escalationSymbol=driver.findElements(ticketListPage.getEscalationSymbol());
                for(WebElement symbol:escalationSymbol){
                    boolean check=symbol.getText().equalsIgnoreCase("!") || symbol.getText().equalsIgnoreCase("!!") || symbol.getText().equalsIgnoreCase("!!!");
                    System.out.println("State: "+check);
                    if(check){
                        ExtentTestManager.getTest().log(LogStatus.PASS,"Ticket Escalation Symbol Validated");
                    }
                }
            }else{
                System.out.println("No Ticket Found for Selected Filter");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
