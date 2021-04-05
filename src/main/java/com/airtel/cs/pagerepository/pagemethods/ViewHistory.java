package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ViewHistoryElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class ViewHistory extends BasePage {
    By firstIssueCode = By.xpath("//tbody/tr[1]/td[7]/p");
    By interactionsTab = By.xpath("//div[@class=\"mat-tab-label-content\" and contains(text(),\"Interaction\")]");
    By ticketHistory = By.xpath("//div[contains(text(),'Ticket')] | //span[contains(text(),'Ticket')]");
    By allIssue = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr");
    By ticketId = By.xpath("//table[@id=\"fetchInteractionByCustomer\"]//tbody//tr[1]//td[8]//span[1]//span[1]");
    By ticketPageTitle = By.xpath("//h2[contains(text(),'View Ticket')]");
    By closeTicketTab = By.xpath("//button[@class='close-btn']//img");
    By messageHistory = By.xpath("//div[contains(text(),'Message')]");
    By actionTrailTab = By.xpath("//div[contains(text(),'Action')]");

    public ViewHistoryElements viewHistoryElements;

    public ViewHistory(WebDriver driver) {
        super(driver);
        viewHistoryElements = PageFactory.initElements(driver, ViewHistoryElements.class);
    }

    public void clickOnInteractionsTab() {
        log.info("Clicking on Interactions Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(interactionsTab);
    }

    /*
    This Method will give you the row(Row May be interaction,issue or message count under view history) count
     */
    public Integer getRowCount() {
        final String paginationNumber = readText(viewHistoryElements.paginationDetails);
        final int length = paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).length();
        paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).substring(10, length);
        return Integer.parseInt(paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).substring(10, length));
    }

    public FrontendTicketHistory clickOnTicketHistory() {
        log.info("Clicking on Ticket History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(ticketHistory);
        return new FrontendTicketHistory(driver);
    }

    public MessageHistoryTabPOM clickOnMessageHistory() {
        UtilsMethods.printInfoLog("Clicking on Message History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(messageHistory);
        return new MessageHistoryTabPOM(driver);
    }

    public ActionTrailTabPOM clickOnActionTrailHistory() {
        UtilsMethods.printInfoLog("Clicking on Action Trail History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(actionTrailTab);
        return new ActionTrailTabPOM(driver);
    }

    public String getLastCreatedIssueCode() {
        UtilsMethods.printInfoLog("Getting the issue code of last created FTR interaction ");
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
        UtilsMethods.printInfoLog("Clicking on ticket icon");
        click(element);
    }

    public boolean clickOnTicketIcon() {
        try {
            List<WebElement> list = returnListOfElement(allIssue);
            for (int i = 1; i <= list.size(); i++) {
                if (!nftrIssueValue(i).equalsIgnoreCase("ftr")) {
                    UtilsMethods.printInfoLog("Clicking on Ticket NFTR ticket icon" + nftrIssueValue(i));
                    clickTicketIcon(i);
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Something went wrong");
        }
        UtilsMethods.printWarningLog("No any NFTR issue found");
        return false;
    }

    public boolean checkViewTicketPage() {
        UtilsMethods.printInfoLog("Checking View Ticket Page");
        return checkState(ticketPageTitle);
    }

    public void clickCloseTicketTab() {
        log.info("closing ticket tab");
        click(closeTicketTab);
    }

    /*
    This Method will route you to Action Trail Tab when You are under View History Tab
     */
    public void goToActionTrail() {
        if (isVisible(viewHistoryElements.actionTrailTab)) {
            click(viewHistoryElements.actionTrailTab);
        }
    }
}
