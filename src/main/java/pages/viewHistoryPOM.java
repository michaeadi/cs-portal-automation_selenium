package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class viewHistoryPOM extends BasePage {
    By firstIssueCode = By.xpath("//tbody/tr[1]/td[7]/p");
    By interactionsTab = By.xpath("//div[@class=\"mat-tab-label-content\" and contains(text(),\"Interaction\")]");
    By ticketHistory = By.xpath("//div[contains(text(),'Ticket')] | //span[contains(text(),'Ticket')]");
    By allIssue = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr");
    By ticketId = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr[1]//td[8]//span[1]//span[1]");
    By ticketPageTitle = By.xpath("//h2[contains(text(),'View Ticket')]");
    By closeTicketTab = By.xpath("//button[@class='close-btn']//img");
    By messageHistory = By.xpath("//div[contains(text(),'Message')]");

    public viewHistoryPOM(WebDriver driver) {
        super(driver);
    }

    public void clickOnInteractionsTab() {
        log.info("Clicking on Interactions Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(interactionsTab);
    }

    public FrontendTicketHistory clickOnTicketHistory() {
        log.info("Clicking on Ticket History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(ticketHistory);
        return new FrontendTicketHistory(driver);
    }

    public MessageHistoryTabPOM clickOnMessageHistory() {
        log.info("Clicking on Message History Tab under view history ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Message History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(messageHistory);
        return new MessageHistoryTabPOM(driver);
    }

    public String getLastCreatedIssueCode() {
        log.info("Getting the issue code of last created FTR interaction ");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Getting the issue code of last created FTR interaction ");
        waitTillLoaderGetsRemoved();
        return readText(firstIssueCode);
    }

    public String ftrIssueValue(int index) {
        By element = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr[" + index + "]//td[9]//span//span");
        String value = driver.findElement(element).getAttribute("title");
        log.info("Reading Attribute Value: " + value);
        return value;
    }

    public String nftrIssueValue(int index) {
        By element = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr[" + index + "]//td[9]//span//span");
        String value = driver.findElement(element).getAttribute("title");
        log.info("Reading Attribute Value: " + value);
        return value;
    }


    public void clickTicketIcon(int index) {
        By element = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr[" + index + "]//td[9]//span//span");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on ticket icon");
        click(element);
    }

    public boolean clickOnTicketIcon() {
        try {
            List<WebElement> list = returnListOfElement(allIssue);
            for (int i = 1; i <= list.size(); i++) {
                if (!nftrIssueValue(i).equalsIgnoreCase("ftr")) {
                    ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Ticket NFTR ticket icon" + nftrIssueValue(i));
                    clickTicketIcon(i);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Something went wrong");
        }
        ExtentTestManager.getTest().log(LogStatus.WARNING, "No any NFTR issue found");
        return false;
    }

    public boolean checkViewTicketPage() {
        log.info("Checking View Ticket Page");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking View Ticket Page");
        return checkState(ticketPageTitle);
    }

    public void clickCloseTicketTab() {
        log.info("closing ticket tab");
        click(closeTicketTab);
    }

}
