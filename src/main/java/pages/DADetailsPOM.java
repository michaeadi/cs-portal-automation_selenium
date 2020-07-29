package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class
DADetailsPOM extends BasePage {
    By rows = By.xpath("//div[@class=\"card__card-header--card-body--table--data-list ng-star-inserted\"]");
    By daID = By.xpath("div[@class=\"ng-star-inserted\"][1]/span");
    By daDescription = By.xpath("div[@class=\"ng-star-inserted\"][2]/span");
    By bundleType = By.xpath("div[@class=\"ng-star-inserted\"][3]/span");
    By daBalance = By.xpath("div[@class=\"ng-star-inserted\"][4]/span");
    By dateTime = By.xpath("div[@class=\"ng-star-inserted\"][5]/span");
    By home = By.xpath("//div[text()=\"HOME\"]");
    List<WebElement> Rows = driver.findElements(rows);

    public DADetailsPOM(WebDriver driver) {
        super(driver);
    }

    public String getDAId(int RowNumber) {
        WebElement rowElement = Rows.get(RowNumber);
        log.info("Getting DA ID from Row Number " + RowNumber + " : " + rowElement.findElement(daID).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting DA ID from Row Number " + RowNumber + " : " + rowElement.findElement(daID).getText());
        return rowElement.findElement(daID).getText();
    }

    public String getDADescription(int RowNumber) {
        WebElement rowElement = Rows.get(RowNumber);
        log.info("Getting DA Description from Row Number " + RowNumber + " : " + rowElement.findElement(daDescription).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting DA Description from Row Number " + RowNumber + " : " + rowElement.findElement(daDescription).getText());
        return rowElement.findElement(daDescription).getText();
    }

    public String getBundleType(int RowNumber) {
        WebElement rowElement = Rows.get(RowNumber);
        log.info("Getting DA Bundle Type from Row Number " + RowNumber + " : " + rowElement.findElement(bundleType).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting DA Bundle Type from Row Number " + RowNumber + " : " + rowElement.findElement(bundleType).getText());
        return rowElement.findElement(bundleType).getText();
    }

    public String getDABalance(int RowNumber) {
        WebElement rowElement = Rows.get(RowNumber);
        log.info("Getting DA Balance from Row Number " + RowNumber + " : " + rowElement.findElement(daBalance).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting DA Balance from Row Number " + RowNumber + " : " + rowElement.findElement(daBalance).getText());
        return rowElement.findElement(daBalance).getText();
    }

    public String getDADateTime(int RowNumber) {
        WebElement rowElement = Rows.get(RowNumber);
        log.info("Getting DA Date and Time from Row Number " + RowNumber + " : " + rowElement.findElement(dateTime).getText());
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting DA and Time from Row Number " + RowNumber + " : " + rowElement.findElement(dateTime).getText());
        return rowElement.findElement(dateTime).getText();
    }

    public customerInteractionPagePOM openingCustomerInteractionDashboard() {
        log.info("Opening Customer Interactions Dashboard");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Opening Customer Interactions Dashboard");
        click(home);
        waitTillLoaderGetsRemoved();
        return new customerInteractionPagePOM(driver);
    }

    public int getNumbersOfRows() {
        return Rows.size();
    }
}
