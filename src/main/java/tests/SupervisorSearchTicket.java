package tests;

import API.APIEndPoints;
import POJO.TicketList.IssueDetails;
import POJO.TicketList.TicketPOJO;
import Utils.DataProviders.DataProviders;
import Utils.DataProviders.nftrDataBeans;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.supervisorTicketListPagePOM;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupervisorSearchTicket extends BaseTest {


    APIEndPoints api = new APIEndPoints();

    @Test(priority = 1, description = "Ticket Search ", dataProvider = "ticketId", dataProviderClass = DataProviders.class)
    public void SearchTicket(Method method, nftrDataBeans Data) {
        ExtentTestManager.startTest("Search Ticket & Validate Ticket Meta Data: " + Data.getTicketNumber(), "Search Ticket & Validate Ticket Meta Data");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening URL");
        supervisorTicketListPagePOM ticketListPage = new supervisorTicketListPagePOM(driver);
        SoftAssert softAssert = new SoftAssert();
        Map<String, String> workGroups = new HashMap<>();
        DataProviders dataProviders = new DataProviders();
        try {
            ticketListPage.writeTicketId(Data.getTicketNumber());
            ticketListPage.clickSearchBtn();
            ticketListPage.waitTillLoaderGetsRemoved();
            Assert.assertEquals(ticketListPage.getTicketIdvalue(), Data.getTicketNumber(), "Search Ticket does not found");

            try {
                TicketPOJO ticketPOJO = api.ticketMetaDataTest(Data.getTicketNumber());
                softAssert.assertTrue(ticketListPage.isTicketIdLabel(), "Ticket Meta Data Does Not Have Ticket Id");
                softAssert.assertTrue(ticketListPage.isWorkGroupName(), "Ticket Meta Data Does Not Have Workgroup");
                softAssert.assertTrue(ticketListPage.isPrioritylabel(), "Ticket Meta Data Does Not Have Priority");
                softAssert.assertTrue(ticketListPage.isStateLabel(), "Ticket Meta Data Does Not Have State");
                softAssert.assertTrue(ticketListPage.isCreationdateLabel(), "Ticket Meta Data Does Not Have Creation Date");
                softAssert.assertTrue(ticketListPage.isCreatedbyLabel(), "Ticket Meta Data Does Not Have Created By");
                softAssert.assertTrue(ticketListPage.isQueueLabel(), "Ticket Meta Data Does Not Have Queue");
                softAssert.assertTrue(ticketListPage.isIssueLabel(), "Ticket Meta Data Does Not Have Issue");
                softAssert.assertTrue(ticketListPage.isIssueTypeLabel(), "Ticket Meta Data Does Not Have Issue Type");
                softAssert.assertTrue(ticketListPage.isSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Type");
                softAssert.assertTrue(ticketListPage.isSubSubTypeLabel(), "Ticket Meta Data Does Not Have Issue Sub Sub Type");
                softAssert.assertTrue(ticketListPage.isCodeLabel(), "Ticket Meta Data Does Not Have Code");
                softAssert.assertEquals(ticketListPage.getIssueValue().toLowerCase().trim(), Data.getIssue().toLowerCase().trim(),
                        "Issue Does Not Validated");
                softAssert.assertEquals(ticketListPage.getIssueTypeValue().toLowerCase().trim(), Data.getIssueType().toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(ticketListPage.getSubTypeValue().toLowerCase().trim(), Data.getIssueSubType().toLowerCase().trim(),
                        "Issue Does Not Sub Type Validated");
                softAssert.assertEquals(ticketListPage.getsubSubTypeValue().toLowerCase().trim(), Data.getIssueSubSubType().toLowerCase().trim(),
                        "Issue Does Not Sub Sub Type Validated");
                softAssert.assertEquals(ticketListPage.getCodeValue().toLowerCase().trim(), Data.getIssueCode().toLowerCase().trim(),
                        "Issue Does Not Code Validated");
                softAssert.assertEquals(ticketListPage.getWorkGroupName().toLowerCase().trim(), Data.getWorkgroup1().toLowerCase().trim(),
                        "Ticket Does Not WorkGroup Validated");
                softAssert.assertEquals(ticketListPage.getqueueValue().toLowerCase().trim(), Data.getAssignmentQueue().toLowerCase().trim(),
                        "Ticket Does Not Queue Validated");
                softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                        "Ticket Does Not Priority Validated");
                softAssert.assertEquals(ticketListPage.getPriorityValue().toLowerCase().trim(), Data.getPriority().toLowerCase().trim(),
                        "Issue Does Not Type Validated");
                softAssert.assertEquals(ticketListPage.convertToHR(ticketPOJO.getResult().getCommittedSla()), Data.getCommittedSLA(), "Committed SLA does not configured Correctly");
                softAssert.assertEquals(ticketListPage.getMSISDN().trim(), ticketPOJO.getResult().getMsisdn().trim(), "User MSISDN is not same as expected");
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
                ArrayList<IssueDetails> ticketLayout = ticketPOJO.getResult().getTicketDetails();
                List<String> configTicketLayout = dataProviders.getTicketLayout(Data.getIssueCode());
                try {
                    for (IssueDetails layout : ticketLayout) {
                        if (!configTicketLayout.contains(layout.getPlaceHolder().toLowerCase().trim())) {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, layout.getPlaceHolder() + " : Ticket Layout must not configure in database as does not mention in Config sheet.");
                        }
                        configTicketLayout.remove(layout.getPlaceHolder().toLowerCase().trim());
                    }
                } catch (NullPointerException e) {
                    ticketListPage.printInfoLog("No Ticket Layout Config in database :" + e.getMessage());
                }

                for (String name : configTicketLayout) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, name + " : Ticket Layout must be configure in database as mention in Config sheet.");
                }
            } catch (NullPointerException | TimeoutException | NoSuchElementException e) {
                e.printStackTrace();
                softAssert.fail("Ticket meta data Assertion failed: " + e.getMessage());
            }
        } catch (TimeoutException | NoSuchElementException | AssertionError e) {
            e.printStackTrace();
            String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                    getScreenshotAs(OutputType.BASE64);
            ExtentTestManager.getTest().log(LogStatus.INFO, ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
            softAssert.fail("Ticket id search not done for following error: " + e.getMessage());
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
        softAssert.assertTrue(ticketListPage.isAssignToAgent(), "Check User does not have Option to Assign to Agent");
        softAssert.assertTrue(ticketListPage.isTransferToQueue(), "Check User does not have Option to Transfer to Queue");
        softAssert.assertAll();
    }


}
