package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.FrontendTicketHistoryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class FrontendTicketHistory extends BasePage {

    FrontendTicketHistoryPage pageElements;
    private static final String XPATH = "//table[@id='fetchTicketByCustomer']//tbody//tr[";

    public FrontendTicketHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, FrontendTicketHistoryPage.class);
    }

    public String getTicketId(int index) {
        By ticketID = By.xpath(XPATH + index + "]//td[1]//p[1]//b[1]");
        final String text = getText(ticketID);
        commonLib.info("Reading Ticket Id: " + text);
        return text;
    }

    public String getTicketPriority(int index) {
        By ticketPriority = By.xpath(XPATH + index + "]//td[2]//p[1]//b[1]");
        final String text = getText(ticketPriority);
        commonLib.info("Reading Ticket Priority: " + text);
        return text;
    }


    public String getTicketQueue(int index) {
        By ticketQueue = By.xpath(XPATH + index + "]//td[3]//p[1]");
        final String text = getText(ticketQueue);
        commonLib.info("Reading Ticket Queue: " + text);
        return text;
    }

    public String getTicketState(int index) {
        By ticketState = By.xpath(XPATH + index + "]//td[4]//p[1]");
        final String text = getText(ticketState);
        commonLib.info("Reading Ticket state: " + text);
        return text;
    }

    public String getCloseDate(int index) {
        By ticketCloseDate = By.xpath(XPATH + index + "]//td[5]//p[1]//span[1]");
        final String text = getText(ticketCloseDate);
        commonLib.info("Reading Ticket close date: " + text);
        return text;
    }

    public String getCloseTime(int index) {
        By ticketCloseTime = By.xpath(XPATH + index + "]//td[5]//p[1]");
        final String text = getText(ticketCloseTime);
        commonLib.info("Reading Ticket close time: " + text);
        return text;
    }

    public String getCreatedBy(int index) {
        By ticketCreatedBy = By.xpath(XPATH + index + "]//td[6]//p[1]");
        final String text = getText(ticketCreatedBy);
        commonLib.info("Reading Ticket created by: " + text);
        return text;
    }

    public String getCreationDate(int index) {
        By ticketCreationDate = By.xpath(XPATH + index + "]//td[7]//p[1]//span[1]");
        final String text = getText(ticketCreationDate);
        commonLib.info("Reading Ticket Creation date: " + text);
        return text;
    }

    public String getCreationTime(int index) {
        By ticketCreationTime = By.xpath(XPATH + index + "]//td[7]//p[1]");
        final String text = getText(ticketCreationTime);
        commonLib.info("Reading Ticket Creation time: " + text);
        return text;
    }

    public void clickAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath(XPATH + index + "]//td[8]//span[1]//a[1]//img");
        commonLib.info("Clicking on Add to interaction button");
        click(addToInteractionBtn);
    }

    public boolean checkAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath(XPATH + index + "]//td[8]//span[1]//a[1]//img");
        String ticketId = getTicketId(index);
        commonLib.info("Checking Add to interaction on ticket id: " + ticketId);
        return isEnabled(addToInteractionBtn);
    }

    public boolean checkReopen(int index) {
        By reopenBtn = By.xpath(XPATH + index + "]//td[8]//span[1]//a[2]//img");
        commonLib.info("Checking reopen button on ticket");
        return isEnabled(reopenBtn);
    }

    public void clickReopen(int index) {
        By reopenBtn = By.xpath(XPATH + index + "]//td[8]//span[1]//a[2]//img");
        commonLib.info("Clicking on Add to interaction button");
        click(reopenBtn);
    }

    public String getSourceApp(int index) {
        By check = By.xpath(XPATH + index + "]//td[8]/p");
        final String text = getText(check);
        commonLib.info("Reading Source APP: " + text);
        return text;
    }


    public void writeTicketId(String id) {
        commonLib.info("Writing Ticket Id:" + id);
        enterText(pageElements.searchBox, id);
    }

    public void clickSearchBtn() {
        commonLib.info("Clicking Search button");
        click(pageElements.searchBtn);
    }

    public boolean checkNoTicketFound() {
        log.info("Checking Ticket Found or not");
        try {
            return isEnabled(pageElements.noTicketFound);
        } catch (Exception e) {
            log.info("No Ticket Found: " + false);
            return false;
        }
    }

    public void clickClearSearchBox() {
        commonLib.info("Click on clear search box button");
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
                commonLib.pass("Add to interaction icon present on tickets");
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        commonLib.error("Add to interaction icon does not present on tickets", false);
        return false;
    }

    public void clickToOpenTicketTicketHistory() {
        click(pageElements.ticketRow);
        waitTillLoaderGetsRemoved();
    }

}