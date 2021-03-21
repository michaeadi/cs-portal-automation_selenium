package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.*;
import org.testng.Assert;

import java.util.List;

@Log4j2
public class
DADetailsPOM extends BasePage {
    By rows = By.xpath("//span[contains(text(),'DA Details')]//ancestor::div[2]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"]");
    By daID = By.xpath("div[@class=\"ng-star-inserted\"][1]/span");
    By bundleType = By.xpath("div[@class=\"ng-star-inserted\"][3]/span");
    List<WebElement> Rows = returnListOfElement(rows);
    By ticketIcon = By.xpath("//span[@class=\"card__card-header--label\" and contains(text(),'DA')]//ancestor::div[1]//span/img[@class='interaction-ticket']");
    By getTitle = By.xpath("//span[contains(text(),'DA Details')]");
    /*
     * Accumulator Widget Element Locator
     * */


    public DADetailsPOM(WebDriver driver) {
        super(driver);
    }

    public String getHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span"));
        log.info("Getting header Number " + row + " : " + header);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting header Number " + row + " : " + header);
        return header;
    }

    public String getDAId(int RowNumber) {
        By daId = By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][1]/span");
        printInfoLog("Getting DA ID from Row Number " + RowNumber + " : " + readText(daId));
        return readText(daId);
    }

    public String getDADescription(int RowNumber) {
        By daDescription = By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][2]/span");
        printInfoLog("Getting DA Description from Row Number " + RowNumber + " : " + readText(daDescription));
        return readText(daDescription);
    }

    public String getBundleType(int RowNumber) {
        By bundleType = By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][3]/span");
        printInfoLog("Getting DA Bundle Type from Row Number " + RowNumber + " : " + readText(bundleType));
        return readText(bundleType);
    }

    public String getDABalance(int RowNumber) {
        By daBalance = By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][4]/span");
        printInfoLog("Getting DA Balance from Row Number " + RowNumber + " : " + readText(daBalance));
        return readText(daBalance);
    }

    public String getDADateTime(int RowNumber) {
        By dateTime = By.xpath("//span[contains(text(),\"DA Details\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"][" + RowNumber + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][5]/span");
        printInfoLog("Getting DA Date and Time from Row Number " + RowNumber + " : " + readText(dateTime));
        return readText(dateTime);
    }


    public int getNumbersOfRows() {
        return Rows.size();
    }

    public WidgetInteractionPOM clickTicketIcon() {
        try {
            log.info("Clicking on Ticket Icon");
            ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Ticket Icon");
            click(ticketIcon);
            return new WidgetInteractionPOM(driver);
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ticket Icon does not display on DA History Widget");
        }
        return null;
    }

    public String getWidgetTitle() {
        log.info("Getting Widget title: " + readText(getTitle));
        return readText(getTitle).toLowerCase();
    }

    public boolean isDAWidgetIsVisible() {
        log.info("Checking is DA Widget Visible");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking is DA Widget Visible");
        return isElementVisible(getTitle);
    }

    public String getAccumulatorHeaders(int row) {
        String header = readText(By.xpath("//span[contains(text(),\"Accumulators\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"card__card-header--card-body--table--list-heading ng-star-inserted\"]//div[" + row + "]/span"));
        printInfoLog("Getting Accumulator header Number " + row + " : " + header);
        return header;
    }

    public String getValueCorrespondingToAccumulator(int row, int column) {
        String value = readText(By.xpath("//span[contains(text(),\"Accumulators\")]//ancestor::div[@class=\"card widget ng-star-inserted\"]//div[@class=\"table-data-wrapper ng-star-inserted\"]/div[@class=\"ng-star-inserted\"  or @class=\"slide-toggle red ng-star-inserted\"][" + row + "]//div[@class=\"ng-star-inserted\" or @class=\"slide-toggle red ng-star-inserted\"][" + column + "]/span"));
        printInfoLog("Reading '" + getAccumulatorHeaders(column) + "' = " + value);
        return value.trim();
    }
}
