package tests;

import Utils.DataProviders.DataProviders;
import Utils.DataProviders.HeaderDataBean;
import Utils.DataProviders.TestDatabean;
import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class WidgetTaggedIssueTest extends BaseTest {

    @DataProviders.User(UserType = "NFTR")
    @Test(priority = 1, description = "Validate Customer Interaction Page", dataProvider = "loginData", dataProviderClass = DataProviders.class)
    public void openCustomerInteraction(TestDatabean Data) {
        ExtentTestManager.startTest("Validating the Search forCustomer Interactions :" + Data.getCustomerNumber(), "Validating the Customer Interaction Search Page By Searching Customer number : " + Data.getCustomerNumber());
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM SideMenuPOM = new SideMenuPOM(driver);
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        softAssert.assertAll();
    }

    @Test(priority = 2, description = "Validating Current Plan widget tagged issue")
    public void currentPlanWidgetTagIssue() {
        ExtentTestManager.startTest("Validating Current Plan widget tagged issue", "Validating Current Plan widget tagged issue");
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, String> tagIssue = data.getListOfIssue(config.getProperty("currentPlan"));
        softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetVisible(), "Current Balance Widget is not visible ");
        String widgetName = currentBalanceWidget.getWidgetTitle();
        WidgetInteractionPOM tagIssueTab = currentBalanceWidget.clickTicketIcon();
        softAssert.assertTrue(tagIssueTab.getTabTitle().toLowerCase().contains(widgetName), "Interaction tab does not have title displayed");
        try {
            if (tagIssueTab.checkNoInteractionTag()) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "No Interaction tagged with widget");
            } else {
                List<String> visibleIssueList = tagIssueTab.getListOfIssue();
                try {
                    for (String s : visibleIssueList) {
                        if (tagIssue.containsKey(s)) {
                            ExtentTestManager.getTest().log(LogStatus.PASS, "Validate " + s + " :Issue tag to widget is display correctly");
                            tagIssue.remove(s);
                        } else {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                            softAssert.fail(s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tagIssue.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All issue tagged to widget correctly configured and display on UI.");
            } else {
                for (Map.Entry<String, String> mapElement : tagIssue.entrySet()) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " issue does not display on UI but present in config sheet.");
                    softAssert.fail(mapElement.getKey() + " :Issue does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.INFO, e.fillInStackTrace());
        }
        tagIssueTab.closeInteractionTab();
        softAssert.assertAll();
    }

    @Test(priority = 3, description = "Validating Recharge History widget tagged issue")
    public void rechargeHistoryWidgetTagIssue() {
        ExtentTestManager.startTest("Validating Recharge History widget tagged issue", "Validating Recharge History widget tagged issue");
        RechargeHistoryWidgetPOM rechargeHistory = new RechargeHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, String> tagIssue = data.getListOfIssue(config.getProperty("rechargeHistory"));
        softAssert.assertTrue(rechargeHistory.isRechargeHistoryWidgetIsVisible(), "Recharge History Widget is not visible ");
        String widgetName = rechargeHistory.getWidgetTitle();
        WidgetInteractionPOM tagIssueTab = rechargeHistory.clickTicketIcon();
        softAssert.assertTrue(tagIssueTab.getTabTitle().toLowerCase().contains(widgetName), "Interaction tab does not have title displayed");
        try {
            if (tagIssueTab.checkNoInteractionTag()) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "No Interaction tagged with widget");
            } else {
                List<String> visibleIssueList = tagIssueTab.getListOfIssue();
                try {
                    for (String s : visibleIssueList) {
                        if (tagIssue.containsKey(s)) {
                            ExtentTestManager.getTest().log(LogStatus.PASS, "Validate " + s + " :Issue tag to widget is display correctly");
                            tagIssue.remove(s);
                        } else {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                            softAssert.fail(s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tagIssue.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All issue tagged to widget correctly configured and display on UI.");
            } else {
                for (Map.Entry<String, String> mapElement : tagIssue.entrySet()) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " issue does not display on UI but present in config sheet.");
                    softAssert.fail(mapElement.getKey() + " :Issue does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.INFO, e.fillInStackTrace());
        }
        tagIssueTab.closeInteractionTab();
        softAssert.assertAll();
    }

    @Test(priority = 4, description = "Validating Usage History widget tagged issue", dataProviderClass = DataProviders.class)
    public void usageWidgetTagIssue() {
        ExtentTestManager.startTest("Validating Usage History widget tagged issue", "Validating Usage History widget tagged issue");
        UsageHistoryWidgetPOM usageHistory = new UsageHistoryWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, String> tagIssue = data.getListOfIssue(config.getProperty("usageHistory"));
        softAssert.assertTrue(usageHistory.isUsageHistoryWidgetIsVisible(), "Usage History Widget is not visible ");
        String widgetName = usageHistory.getWidgetTitle();
        WidgetInteractionPOM tagIssueTab = usageHistory.clickTicketIcon();
        softAssert.assertTrue(tagIssueTab.getTabTitle().toLowerCase().contains(widgetName), "Interaction tab does not have title displayed");
        try {
            if (tagIssueTab.checkNoInteractionTag()) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "No Interaction tagged with widget");
            } else {
                List<String> visibleIssueList = tagIssueTab.getListOfIssue();
                try {
                    for (String s : visibleIssueList) {
                        if (tagIssue.containsKey(s)) {
                            ExtentTestManager.getTest().log(LogStatus.PASS, "Validate " + s + " :Issue tag to widget is display correctly");
                            tagIssue.remove(s);
                        } else {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                            softAssert.fail(s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tagIssue.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All issue tagged to widget correctly configured and display on UI.");
            } else {
                for (Map.Entry<String, String> mapElement : tagIssue.entrySet()) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " issue does not display on UI but present in config sheet.");
                    softAssert.fail(mapElement.getKey() + " :Issue does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.INFO, e.fillInStackTrace());
        }
        tagIssueTab.closeInteractionTab();
        softAssert.assertAll();
    }

    @Test(priority = 5, description = "Validating Airtel Money widget tagged issue")
    public void airtelMoneyWidgetTagIssue() {
        ExtentTestManager.startTest("Validating Airtel Money widget tagged issue", "Validating Airtel Money widget tagged issue");
        AMTransactionsWidgetPOM airtelMoney = new AMTransactionsWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, String> tagIssue = data.getListOfIssue(config.getProperty("airtelMoney"));
        softAssert.assertTrue(airtelMoney.isAirtelMoneyTransactionWidgetIsVisible(), "Airtel Money Widget is not visible ");
        String widgetName = airtelMoney.getWidgetTitle();
        WidgetInteractionPOM tagIssueTab = airtelMoney.clickTicketIcon();
        softAssert.assertTrue(tagIssueTab.getTabTitle().toLowerCase().contains(widgetName), "Interaction tab does not have title displayed");
        try {
            if (tagIssueTab.checkNoInteractionTag()) {
                ExtentTestManager.getTest().log(LogStatus.WARNING, "No Interaction tagged with widget");
            } else {
                List<String> visibleIssueList = tagIssueTab.getListOfIssue();
                try {
                    for (String s : visibleIssueList) {
                        if (tagIssue.containsKey(s)) {
                            ExtentTestManager.getTest().log(LogStatus.PASS, "Validate " + s + " :Issue tag to widget is display correctly");
                            tagIssue.remove(s);
                        } else {
                            ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                            softAssert.fail(s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (tagIssue.isEmpty()) {
                ExtentTestManager.getTest().log(LogStatus.PASS, "All issue tagged to widget correctly configured and display on UI.");
            } else {
                for (Map.Entry<String, String> mapElement : tagIssue.entrySet()) {
                    ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " issue does not display on UI but present in config sheet.");
                    softAssert.fail(mapElement.getKey() + " :Issue does not display on UI but present in config sheet.");
                }
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.INFO, e.fillInStackTrace());
        }
        tagIssueTab.closeInteractionTab();
        softAssert.assertAll();
    }

    @Test(priority = 6, description = "Validating DA Detail widget tagged issue")
    public void daDetailsTest() {
        ExtentTestManager.startTest("Validating DA Detail widget tagged issue", "Validating DA Detail widget tagged issue");
        CurrentBalanceWidgetPOM currentBalanceWidget = new CurrentBalanceWidgetPOM(driver);
        SoftAssert softAssert = new SoftAssert();
        DataProviders data = new DataProviders();
        Map<String, String> tagIssue = data.getListOfIssue(config.getProperty("daDetails"));
        try {
            softAssert.assertTrue(currentBalanceWidget.isCurrentBalanceWidgetMenuVisible(), "Current Balance Widget MENU is not visible ");
            currentBalanceWidget.clickingCurrentBalanceWidgetMenu();
            softAssert.assertTrue(currentBalanceWidget.isDADetailsMenuVisible(), "DA Details Option in  MENU is not visible ");
            DADetailsPOM daDetails = currentBalanceWidget.openingDADetails();
            softAssert.assertTrue(daDetails.isDAWidgetIsVisible());
            String widgetName = daDetails.getWidgetTitle();
            WidgetInteractionPOM tagIssueTab = daDetails.clickTicketIcon();
            softAssert.assertTrue(tagIssueTab.getTabTitle().toLowerCase().contains(widgetName), "Interaction tab does not have title displayed");
            try {
                if (tagIssueTab.checkNoInteractionTag()) {
                    ExtentTestManager.getTest().log(LogStatus.WARNING, "No Interaction tagged with widget");
                } else {
                    List<String> visibleIssueList = tagIssueTab.getListOfIssue();
                    try {
                        for (String s : visibleIssueList) {
                            if (tagIssue.containsKey(s)) {
                                ExtentTestManager.getTest().log(LogStatus.PASS, "Validate " + s + " :Issue tag to widget is display correctly");
                                tagIssue.remove(s);
                            } else {
                                ExtentTestManager.getTest().log(LogStatus.FAIL, s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                                softAssert.fail(s + " :Issue must not tag to widget and display on UI as this is not mention in config sheet.");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (tagIssue.isEmpty()) {
                    ExtentTestManager.getTest().log(LogStatus.PASS, "All issue tagged to widget correctly configured and display on UI.");
                } else {
                    for (Map.Entry<String, String> mapElement : tagIssue.entrySet()) {
                        ExtentTestManager.getTest().log(LogStatus.FAIL, mapElement.getKey() + " issue does not display on UI but present in config sheet.");
                        softAssert.fail(mapElement.getKey() + " :Issue does not display on UI but present in config sheet.");
                    }
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                ExtentTestManager.getTest().log(LogStatus.INFO, e.fillInStackTrace());
            } finally {
                tagIssueTab.closeInteractionTab();
            }
            daDetails.openingCustomerInteractionDashboard();

        } catch (Exception e) {
            e.printStackTrace();
            ExtentTestManager.getTest().log(LogStatus.FAIL, e.fillInStackTrace());
            softAssert.fail("DA details does not display correctly");
        }
        softAssert.assertAll();

    }
}
