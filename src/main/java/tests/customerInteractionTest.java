package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.TestDatabean;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.customerInteractionPagePOM;
import pages.customerInteractionsSearchPOM;
import pages.sideMenuPOM;

import java.lang.reflect.Method;

public class customerInteractionTest extends BaseTest {
    @Test(priority = 1, description = "SideMenu ", dataProvider = "getTestData")
    public void openCustomerInteraction(Method method, TestDatabean Data) {
        sideMenuPOM SideMenuPOM = new sideMenuPOM(driver);
        ExtentTestManager.startTest(method.getName(), "Opening Customer Interaction");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Asserting Demographic information");
        SoftAssert softAssert = new SoftAssert();
        SideMenuPOM.clickOnSideMenu();
        SideMenuPOM.clickOnName();
        customerInteractionsSearchPOM customerInteractionsSearchPOM = SideMenuPOM.openCustomerInteractionPage();
        customerInteractionsSearchPOM.enterNumber(Data.getCustomerNumber());
        customerInteractionPagePOM customerInteractionPagePOM = customerInteractionsSearchPOM.clickOnSearch();
        softAssert.assertTrue(customerInteractionPagePOM.isPageLoaded());
        System.out.println(customerInteractionPagePOM.getCustomerName());
        softAssert.assertEquals(customerInteractionPagePOM.getCustomerName().trim(), Data.getCustomerName());
        System.out.println(customerInteractionPagePOM.getCustomerDOB());
        softAssert.assertEquals(customerInteractionPagePOM.getCustomerDOB().trim(), Data.getCustomerDOB());
        System.out.println(customerInteractionPagePOM.getActivationDate());
        softAssert.assertEquals(customerInteractionPagePOM.getActivationDate().trim(), Data.getActivationDate());
        System.out.println(customerInteractionPagePOM.getActivationTime());
        softAssert.assertEquals(customerInteractionPagePOM.getActivationTime().trim(), Data.getActivationTime().trim());

        System.out.println(customerInteractionPagePOM.getSimNumber());
        softAssert.assertEquals(customerInteractionPagePOM.getSimNumber().trim(), Data.getSimNumber());

        System.out.println(customerInteractionPagePOM.getSimType());
        softAssert.assertEquals(customerInteractionPagePOM.getSimType().trim(), Data.getSimType());

        System.out.println(customerInteractionPagePOM.getPUK1());
        softAssert.assertEquals(customerInteractionPagePOM.getPUK1().trim(), Data.getPUK1());

        System.out.println(customerInteractionPagePOM.getPUK2());
        softAssert.assertEquals(customerInteractionPagePOM.getPUK2().trim(), Data.getPUK2());

        System.out.println(customerInteractionPagePOM.getIdType());
        softAssert.assertEquals(customerInteractionPagePOM.getIdType().trim(), Data.getIdType());

        System.out.println(customerInteractionPagePOM.getIdNumber());
        softAssert.assertEquals(customerInteractionPagePOM.getIdNumber().trim(), Data.getIdNumber());

        softAssert.assertAll();
    }

}
