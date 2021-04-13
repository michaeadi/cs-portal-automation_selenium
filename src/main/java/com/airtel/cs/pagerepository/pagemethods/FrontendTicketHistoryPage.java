package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.commonutils.extentreports.ExtentTestManager;
import com.airtel.cs.pagerepository.pageelements.FrontendTicketHistoryPageElements;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class FrontendTicketHistoryPage extends BasePage {

    FrontendTicketHistoryPageElements pageElements;

    public FrontendTicketHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, FrontendTicketHistoryPageElements.class);
    }

    public String getTicketId(int index) {
        By ticketID = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[1]//p[1]//b[1]");
        UtilsMethods.printInfoLog("Reading Ticket Id: " + getText(ticketID));
        return getText(ticketID);
    }

    public String getTicketPriority(int index) {
        By ticketPriority = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[2]//p[1]//b[1]");
        UtilsMethods.printInfoLog("Reading Ticket Priority: " + getText(ticketPriority));
        return getText(ticketPriority);
    }


    public String getTicketQueue(int index) {
        By ticketQueue = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[3]//p[1]");
        UtilsMethods.printInfoLog("Reading Ticket Queue: " + getText(ticketQueue));
        return getText(ticketQueue);
    }

    public String getTicketState(int index) {
        By ticketState = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[4]//p[1]");
        UtilsMethods.printInfoLog("Reading Ticket state: " + getText(ticketState));
        return getText(ticketState);
    }

    public String getCloseDate(int index) {
        By ticketCloseDate = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[5]//p[1]//span[1]");
        UtilsMethods.printInfoLog("Reading Ticket close date: " + getText(ticketCloseDate));
        return getText(ticketCloseDate);
    }

    public String getCloseTime(int index) {
        By ticketCloseTime = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[5]//p[1]");
        UtilsMethods.printInfoLog("Reading Ticket close time: " + getText(ticketCloseTime));
        return getText(ticketCloseTime);
    }

    public String getCreatedBy(int index) {
        By ticketCreatedBy = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[6]//p[1]");
        UtilsMethods.printInfoLog("Reading Ticket created by: " + getText(ticketCreatedBy));
        return getText(ticketCreatedBy);
    }

    public String getCreationDate(int index) {
        By ticketCreationDate = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[7]//p[1]//span[1]");
        UtilsMethods.printInfoLog("Reading Ticket Creation date: " + getText(ticketCreationDate));
        return getText(ticketCreationDate);
    }

    public String getCreationTime(int index) {
        By ticketCreationTime = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[7]//p[1]");
        UtilsMethods.printInfoLog("Reading Ticket Creation time: " + getText(ticketCreationTime));
        return getText(ticketCreationTime);
    }

    public void clickAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[1]//img");
        UtilsMethods.printInfoLog("Clicking on Add to interaction button");
        click(addToInteractionBtn);
    }

    public boolean checkAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[1]//img");
        String ticketId = getTicketId(index);
        UtilsMethods.printInfoLog("Checking Add to interaction on ticket id: " + ticketId);
        return checkState(addToInteractionBtn);
    }

    public boolean checkReopen(int index) {
        By reopenBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[2]//img");
        UtilsMethods.printInfoLog("Checking reopen button on ticket");
        return checkState(reopenBtn);
    }

    public void clickReopen(int index) {
        By reopenBtn = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]//span[1]//a[2]//img");
        UtilsMethods.printInfoLog("Clicking on Add to interaction button");
        click(reopenBtn);
    }

    public String getSourceApp(int index) {
        By check = By.xpath("//table[@id='fetchTicketByCustomer']//tbody//tr[" + index + "]//td[8]/p");
        UtilsMethods.printInfoLog("Reading Source APP: " + getText(check));
        return getText(check);
    }


    public void writeTicketId(String id) {
        UtilsMethods.printInfoLog("Writing Ticket Id:" + id);
        writeText(pageElements.searchBox, id);
    }

    public void clickSearchBtn() {
        UtilsMethods.printInfoLog("Clicking Search button");
        click(pageElements.searchBtn);
    }

    public boolean checkNoTicketFound() {
        log.info("Checking Ticket Found or not");
        try {
            return checkState(pageElements.noTicketFound);
        } catch (Exception e) {
            log.info("No Ticket Found: " + false);
            return false;
        }
    }

    public void clickClearSearchBox() {
        UtilsMethods.printInfoLog("Click on clear search box button");
        click(pageElements.clearTicketId);
    }

    public boolean validateAddToInteractionIcon() {
        try {
            if (!checkNoTicketFound()) {
                log.info("Ticket found");
                List<WebElement> list = returnListOfElement(pageElements.allTicket);
                for (int i = 1; i <= list.size(); i++) {
                    if (!checkAddToInteraction(i)) {
                        return false;
                    }
                }
                UtilsMethods.printPassLog("Add to interaction icon present on tickets");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ExtentTestManager.getTest().log(LogStatus.ERROR, "Add to interaction icon does not present on tickets");
        return false;
    }

    public void clickToOpenTicketTicketHistory() {
        click(pageElements.ticketRow);
        waitTillLoaderGetsRemoved();
    }

}