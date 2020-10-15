package tests;

import API.APITest;
import POJO.TicketList.IssueDetails;
import POJO.TicketList.TicketPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.SideMenuPOM;
import pages.agentLoginPagePOM;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupervisorSearchTicket extends BaseTest {


    APITest api = new APITest();

    @Test(priority = 1, description = "Supervisor SKIP Login ")
    public void agentSkipQueueLogin(Method method) throws InterruptedException {
        ExtentTestManager.startTest("Supervisor SKIP Queue Login", "Supervisor SKIP Queue Login");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        SideMenuPOM sideMenu = new SideMenuPOM(driver);
        sideMenu.clickOnSideMenu();
        sideMenu.clickOnName();
        agentLoginPagePOM AgentLoginPagePOM = sideMenu.openSupervisorDashboard();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(AgentLoginPagePOM.isQueueLoginPage(), "Agent redirect to Queue Login Page");
        softAssert.assertTrue(AgentLoginPagePOM.checkSkipButton(), "Checking Queue Login Page have SKIP button");
        softAssert.assertTrue(AgentLoginPagePOM.checkSubmitButton(), "Checking Queue Login Page have Submit button");
        AgentLoginPagePOM.clickSkipBtn();
        Thread.sleep(10000);
        Assert.assertEquals(driver.getTitle(), config.getProperty("supervisorTicketListPage"));
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "agentSkipQueueLogin", description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void SearchTicket(Method method, nftrDataBeans Data) {
        ExtentTestManager.startTest("Search Ticket & Validate Ticket Meta Data", "Search Ticket & Validate Ticket Meta Data");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> workGroups = new HashMap<>();
        DataProviders dataProviders=new DataProviders();
        ticketListPage.writeTicketId(Data.getTicketNumber());
        ticketListPage.clickSearchBtn();
        ticketListPage.waitTillLoaderGetsRemoved();
        Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber());
        TicketPOJO ticketPOJO = api.ticketMetaDataTest(Data.getTicketNumber());
        softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Have Ticket Id");
        softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Have Workgroup");
        softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data Have Priority");
        softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Have State");
        softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Have Creation Date");
        softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Have Created By");
        softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Have Queue");
        softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Have Issue");
        softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Have Issue Type");
        softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Have Issue Sub Type");
        softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Have Issue Sub Sub Type");
        softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Have Code");
        softAssert.assertEquals(ticketListPage.getIssueValue().toLowerCase().trim(), Data.getIssue().toLowerCase().trim(),
                "Issue Validated");
        softAssert.assertEquals(ticketListPage.getIssueTypeValue().toLowerCase().trim(), Data.getIssueType().toLowerCase().trim(),
                "Issue Type Validated");
        softAssert.assertEquals(ticketListPage.getSubTypeValue().toLowerCase().trim(), Data.getIssueSubType().toLowerCase().trim(),
                "Issue Sub Type Validated");
        softAssert.assertEquals(ticketListPage.getsubSubTypeValue().toLowerCase().trim(), Data.getIssueSubSubType().toLowerCase().trim(),
                "Issue Sub Sub Type Validated");
        softAssert.assertEquals(ticketListPage.getCodeValue().toLowerCase().trim(), Data.getIssueCode().toLowerCase().trim(),
                "Issue Code Validated");
        softAssert.assertEquals(ticketListPage.getWorkGroupName().toLowerCase().trim(), Data.getWorkgroup1().toLowerCase().trim(),
                "Ticket WorkGroup Validated");
        softAssert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), Data.getAssignmentQueue().toLowerCase().trim(),
                "Ticket Queue Validated");
        softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                "Ticket Priority Validated");
        softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                "Issue Type Validated");
        softAssert.assertEquals(ticketListPage.convertToHR(ticketPOJO.getResult().getCommittedSla()), Data.getCommittedSLA(), "Committed SLA does not configured Correctly");
        Map<String, Long> sla = ticketPOJO.getResult().getSla();
        if (Data.getWorkgroup1() != null)
            workGroups.put(Data.getWorkgroup1(), Data.getSLA1());
        if (Data.getWorkgroup2() != null)
            workGroups.put(Data.getWorkgroup2(), Data.getSLA2());
        if (Data.getWorkgroup3() != null)
            workGroups.put(Data.getWorkgroup3(), Data.getSLA3());
        if (Data.getWorkgroup4() != null)
            workGroups.put(Data.getWorkgroup4(), Data.getSLA4());
        for (Map.Entry mapElement : sla.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = mapElement.getValue().toString();
            System.out.println(key + " = " + value);
            if (workGroups.containsKey(key)) {
                workGroups.remove(key);
                ExtentTestManager.getTest().log(LogStatus.INFO, key + " : workgroup is configured correctly in DB as mentioned in configuration");
            } else {
                ExtentTestManager.getTest().log(LogStatus.FAIL, key + " workgroup is not configured correctly in DB as not mentioned in configuration");
            }
            if (value.charAt(0) == '-') {
                softAssert.assertTrue(ticketListPage.isNegativeSLA(), "For negative SLA red symbol does not display");
            } else {
                softAssert.assertTrue(ticketListPage.isPositiveSLA(), "For positive SLA green symbol does not display");
            }
        }
        for (Map.Entry mapElement : workGroups.entrySet()) {
            String key = (String) mapElement.getKey();
            String value = mapElement.getValue().toString();
            if (key != null)
                if (!key.isEmpty())
                    ExtentTestManager.getTest().log(LogStatus.FAIL, key + " workgroup is not configured correctly in DB as mentioned in configuration");
        }
        ArrayList<IssueDetails> ticketLayout= ticketPOJO.getResult().getTicketDetails();
        List<String> configTicketLayout=dataProviders.getTicketLayout(Data.getIssueCode());
        for(IssueDetails layout:ticketLayout){
            if(!configTicketLayout.contains(layout.getPlaceHolder().toLowerCase().trim())){
                ExtentTestManager.getTest().log(LogStatus.FAIL,layout.getPlaceHolder()+" : Ticket Layout must not configure in database as does not mention in Config sheet.");
            }
            configTicketLayout.remove(layout.getPlaceHolder().toLowerCase().trim());
        }

        for(String name:configTicketLayout){
            ExtentTestManager.getTest().log(LogStatus.FAIL,name+" : Ticket Layout must be configure in database as mention in Config sheet.");
        }
        ticketListPage.clearInputBox();
        ticketListPage.waitTillLoaderGetsRemoved();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validate Assign to Agent and Transfer to Queue Option", dataProviderClass = DataProviders.class)
    public void validateCheckBox(Method method) {
        ExtentTestManager.startTest("Validate Check Box", "Validate Assign to Agent and Transfer to Queue Option om Open Ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        ticketListPage.clickCheckbox();
        softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Check User have Option to Assign to Agent");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Check User have Option to Transfer to Queue");
        softAssert.assertAll();
    }


}
