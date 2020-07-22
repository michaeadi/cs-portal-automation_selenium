package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

@Log4j2
public class profileManagementPOM extends BasePage {
    By totalProfileHeading = By.xpath("//span[text()=\"Total Profiles\"]");

    public profileManagementPOM(WebDriver driver) {
        super(driver);
    }

    public WebElement getConfigFilterElement() {
        List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id,'mat-select')]"));
        return webElements.get(0);
    }

    public WebElement getRoleStatusFilterElement() {
        List<WebElement> webElements = driver.findElements(By.xpath("//*[starts-with(@id,'mat-select')]"));
        return webElements.get(1);
    }

    public void waitTillPMPageLoads() {
        waitTillLoaderGetsRemoved();
        log.info("Waiting till the profile management page is loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting till the profile management page is loaded");
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalProfileHeading));
    }

    public boolean isProfileConfigFilterPresent() {

        log.info("Checking is Config Filter is Visible on Profile Management Page : " + getConfigFilterElement().isEnabled());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Config Filter is Visible on Profile Management Page : " + getConfigFilterElement().isEnabled());
        return getConfigFilterElement().isEnabled();
    }

    public boolean isRoleStatusFilterPresent() {
        log.info("Checking is Role Status Filter is Visible on Profile Management Page : " + getRoleStatusFilterElement().isEnabled());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is Role Status Filter is Visible on Profile Management Page : " + getRoleStatusFilterElement().isEnabled());
        return getRoleStatusFilterElement().isEnabled();
    }

    public int getNumberOfColumns() {
        List<WebElement> webElements = driver.findElements(By.xpath("//th//span"));
        log.info("Getting total number of Columns available on Profile Management Page : " + webElements.size());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting total number of Columns available on Profile Management Page : " + webElements.size());
        return webElements.size();
    }

    public String getNameOfCol(int ColNo) {
        List<WebElement> webElements = driver.findElements(By.xpath("//th//span"));
        try {
            log.info("Getting Name of Columns available on Profile Management Page : " + webElements.get(ColNo).getText());
            ExtentTestManager.getTest().log(LogStatus.INFO, "Getting Name of Columns available on Profile Management Page : " + webElements.get(ColNo).getText());
            return webElements.get(ColNo).getText();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.out.println("Total Number of columns available" + webElements.size());
            return "Not Found";
        }
    }
}
