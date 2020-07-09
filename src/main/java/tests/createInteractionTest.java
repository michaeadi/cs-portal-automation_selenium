package tests;

import Utils.ExtentReports.ExtentTestManager;
import Utils.ftrDataBeans;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.InteractionsPOM;
import pages.customerInteractionPagePOM;

import java.lang.reflect.Method;

public class createInteractionTest extends BaseTest {


    @Test(priority = 1, description = "Create Interaction ", dataProvider = "getTestData1")
    public void CreateInteraction(Method method, ftrDataBeans Data) throws InterruptedException {
        ExtentTestManager.startTest(method.getName(), "Creating Tickets");
        customerInteractionPagePOM customerInteractionPagePOM = new customerInteractionPagePOM(driver);
        InteractionsPOM interactionsPOM = customerInteractionPagePOM.clickOnInteractionIcon();
        SoftAssert softAssert = new SoftAssert();
        interactionsPOM.clickOnCode();
        interactionsPOM.searchCode(Data.getIssueCode());
        interactionsPOM.selectCode(Data.getIssueCode());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Creating ticket with issue code -" + Data.getIssueCode());
        System.out.println(interactionsPOM.getIssue());
        softAssert.assertEquals(interactionsPOM.getIssue().trim().toLowerCase().replace(" ", ""), Data.getIssue().trim().toLowerCase().replace(" ", ""), "Issue is not as expected ");
        System.out.println(interactionsPOM.getIssueSubSubType());
        softAssert.assertEquals(interactionsPOM.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), Data.getIssueSubSubType().trim().toLowerCase().replace(" ", ""), "Issue sub sub type is not as expected ");
        System.out.println(interactionsPOM.getIssueType());
        softAssert.assertEquals(interactionsPOM.getIssueType().trim().toLowerCase().replace(" ", ""), Data.getIssueType().trim().toLowerCase().replace(" ", ""), "Issue type is not as expected ");
        System.out.println(interactionsPOM.getIssueSubType());
        softAssert.assertEquals(interactionsPOM.getIssueSubType().trim().toLowerCase().replace(" ", ""), Data.getIssueSubType().trim().toLowerCase().replace(" ", ""), "Issue sub type is not as expected ");
        interactionsPOM.sendComment("JMD");
        interactionsPOM.clickOnSave();
        softAssert.assertTrue(interactionsPOM.isResolvedFTRDisplayed());
        String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) driver).
                getScreenshotAs(OutputType.BASE64);
        interactionsPOM.closeInteractions();
        ExtentTestManager.getTest().log(LogStatus.INFO, "Test Failed", ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
        softAssert.assertAll();


    }
}
