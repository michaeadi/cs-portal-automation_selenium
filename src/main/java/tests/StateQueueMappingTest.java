package tests;

import API.APITest;
import POJO.TicketList.QueueStates;
import POJO.TicketList.TicketPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.QueueStateDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StateQueueMappingTest extends BaseTest {

    APITest api = new APITest();

    @Test(priority = 1, description = "Supervisor SKIP Login ", enabled = true)
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

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin",dataProvider = "queueState", description = "State Queue Mapping Test", enabled = true,dataProviderClass = DataProviders.class)
    public void transferToQueue(Method method, QueueStateDataBeans data) throws InterruptedException {
        ExtentTestManager.startTest("State Queue Mapping Test", "State Queue Mapping Test");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        FilterTabPOM filterTab = new FilterTabPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickFilter();
        DataProviders dataProviders=new DataProviders();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.scrollToQueueFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        filterTab.clickQueueFilter();
        filterTab.selectQueueByName(data.getQueue());
        filterTab.clickOutsideFilter();
        filterTab.clickApplyFilter();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertTrue(ticketListPage.validateQueueFilter(data.getQueue()), "Queue Filter Does Applied Correctly");
        Assert.assertEquals(ticketListPage.getqueueValue(), data.getQueue(), "Ticket Does not found with Selected Queue");
        String ticketId = ticketListPage.getTicketIdvalue();
        TicketPOJO ticketPOJO=api.ticketMetaDataTest(ticketId);
        ArrayList<QueueStates> assignState=ticketPOJO.getResult().getQueueStates();
        List<String> state=new ArrayList<>();
        List<String> configState=dataProviders.getQueueState(data.getQueue());
        if(assignState.isEmpty()){
            for(QueueStates s:assignState){
                state.add(s.getExternalStateName());
            }
        }

        for(String s:state){
            if(!configState.contains(s)){
                ExtentTestManager.getTest().log(LogStatus.INFO,s+" :State must not mapped to '"+data.getQueue()+"' as its not mention in config.");
            }
            configState.remove(s);
        }

        for(String s:configState){
            ExtentTestManager.getTest().log(LogStatus.INFO,s+" :State must be mapped to '"+data.getQueue()+"' as its mention in config.");
        }
        softAssert.assertAll();
    }
}
