package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Log4j2
public class customerInteractionsSearchPOM extends BasePage {
    By numberSearch = By.xpath("//input[@name='search']");
    By searchButton = By.xpath("//button[@class='search-icon-btn']");

    public customerInteractionsSearchPOM(WebDriver driver) {
        super(driver);
    }

    public void enterNumber(String number) {
        log.info("Entering Number in search box :" + number);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Entering Number in search box :" + number);
        writeText(numberSearch, number);
    }

    public void clearCustomerNumber(){
        printInfoLog("Clearing Customer Number");
        driver.findElement(numberSearch).clear();
    }

    public customerInteractionPagePOM clickOnSearch() {
        log.info("Clicking on Search Button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Search Button");
        waitTillLoaderGetsRemoved();
        click(searchButton);
        return new customerInteractionPagePOM(driver);
    }

    public void waitUntilPageIsLoaded() {
        waitTillLoaderGetsRemoved();
        log.info("Waiting till the Search Page is Loaded");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Waiting till the Search Page is Loaded");
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    }
}
