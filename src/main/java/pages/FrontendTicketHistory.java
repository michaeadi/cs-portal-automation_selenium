package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

@Log4j2
public class FrontendTicketHistory extends BasePage {

    By searchBox = By.xpath("//input[@class='search-ticket-id ng-untouched ng-pristine ng-valid']");
    By searchBtn = By.xpath("//a[@class='search-btn']");
    By allTicket = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr");
    By noTicketFound = By.xpath("//div[@class=\"no-result-found ng-star-inserted\"]");
    By clearTicketId = By.xpath("//a[@class='search-close ng-star-inserted']//img");

    public FrontendTicketHistory(WebDriver driver) {
        super(driver);
    }

    public String getTicketId(int index) {
        By ticketID = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[1]//p[1]//b[1]");
        log.info("Reading Ticket Id:" + readText(ticketID));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket Id: " + readText(ticketID));
        return readText(ticketID);
    }

    public String getTicketPriority(int index) {
        By ticketPriority = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[2]//p[1]//b[1]");
        log.info("Reading Ticket Priority:" + readText(ticketPriority));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket Priority: " + readText(ticketPriority));
        return readText(ticketPriority);
    }


    public String getTicketQueue(int index) {
        By ticketQueue = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[3]//p[1]");
        log.info("Reading Ticket Queue:" + readText(ticketQueue));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket Queue: " + readText(ticketQueue));
        return readText(ticketQueue);
    }

    public String getTicketState(int index) {
        By ticketState = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[4]//p[1]");
        log.info("Reading Ticket state:" + readText(ticketState));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket state: " + readText(ticketState));
        return readText(ticketState);
    }

    public String getCloseDate(int index) {
        By ticketCloseDate = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[5]//p[1]//span[1]");
        log.info("Reading Ticket close date:" + readText(ticketCloseDate));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket close date: " + readText(ticketCloseDate));
        return readText(ticketCloseDate);
    }

    public String getCloseTime(int index) {
        By ticketCloseTime = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[5]//p[1]");
        log.info("Reading Ticket close time:" + readText(ticketCloseTime));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket close time: " + readText(ticketCloseTime));
        return readText(ticketCloseTime);
    }

    public String getCreatedBy(int index) {
        By ticketCreatedBy = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[6]//p[1]");
        log.info("Reading Ticket created by:" + readText(ticketCreatedBy));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket created by: " + readText(ticketCreatedBy));
        return readText(ticketCreatedBy);
    }

    public String getCreationDate(int index) {
        By ticketCreationDate = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[7]//p[1]//span[1]");
        log.info("Reading Ticket Creation date:" + readText(ticketCreationDate));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket Creation date: " + readText(ticketCreationDate));
        return readText(ticketCreationDate);
    }

    public String getCreationTime(int index) {
        By ticketCreationTime = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[7]//p[1]");
        log.info("Reading Ticket Creation time:" + readText(ticketCreationTime));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Reading Ticket Creation time: " + readText(ticketCreationTime));
        return readText(ticketCreationTime);
    }

    public void clickAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[7]//span[1]//a[1]//img");
        log.info("Clicking on Add to interaction button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Add to interaction button");
        click(addToInteractionBtn);
    }

    public boolean checkAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[1]//img");
        String ticketId = getTicketId(index);
        log.info("Check Add to interaction on ticket id: " + ticketId);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking Add to interaction on ticket id: " + ticketId);
        return checkState(addToInteractionBtn);
    }

    public boolean checkReopen(int index) {
        By reopenBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[2]//img");
        log.info("Checking Reopen button on ticket");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking reopen button on ticket");
        return checkState(reopenBtn);
    }

    public void clickReopen(int index) {
        By reopenBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[2]//img");
        log.info("Clicking on Reopen button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking on Add to interaction button");
        click(reopenBtn);
    }

    public void writeTicketId(String id) {
        log.info("Writing Ticket Id:" + id);
        ExtentTestManager.getTest().log(LogStatus.INFO, "Writing Ticket Id:" + id);
        writeText(searchBox, id);
    }

    public void clickSearchBtn() {
        log.info("Clicking Search button");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Clicking Search button");
        click(searchBtn);
    }

    public boolean checkNoTicketFound() {
        log.info("Checking Ticket Found or not");
        try {
            return checkState(noTicketFound);
        } catch (Exception e) {
            log.info("No Ticket Found: " + false);
            System.out.println("No Ticket Found: " + false);
            return false;
        }
    }

    public void clickClearSearchBox() {
        log.info("Clearing search box");
        ExtentTestManager.getTest().log(LogStatus.INFO, "Click on clear search box button");
        click(clearTicketId);
    }

    public boolean validateAddToInteractionIcon() {
        try {
            if (!checkNoTicketFound()) {
                log.info("Ticket found");
                List<WebElement> list = returnListOfElement(allTicket);
                for (int i = 1; i <= list.size(); i++) {
                    if (!checkAddToInteraction(i)) {
                        return false;
                    }
                }
                ExtentTestManager.getTest().log(LogStatus.PASS, "Add to interaction icon present on tickets");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentTestManager.getTest().log(LogStatus.ERROR, "Add to interaction icon does not present on tickets");
        return false;
    }

}
