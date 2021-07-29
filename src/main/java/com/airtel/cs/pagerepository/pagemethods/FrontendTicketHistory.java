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

    /**
     * This method is use to get ticket id from list based on index
     *
     * @param index The index
     * @return String The value
     */
    public String getTicketId(int index) {
        By ticketID = By.xpath(XPATH + index + pageElements.ticketId);
        final String text = getText(ticketID);
        commonLib.info("Reading Ticket Id: " + text);
        return text;
    }

    /**
     * This method is use to get ticket priority based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getTicketPriority(int index) {
        By ticketPriority = By.xpath(XPATH + index + pageElements.ticketPriority);
        final String text = getText(ticketPriority);
        commonLib.info("Reading Ticket Priority: " + text);
        return text;
    }

    /**
     * This method is use to get ticket queue based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getTicketQueue(int index) {
        By ticketQueue = By.xpath(XPATH + index + pageElements.ticketQueue);
        final String text = getText(ticketQueue);
        commonLib.info("Reading Ticket Queue: " + text);
        return text;
    }

    /**
     * This method is use to get ticket State based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getTicketState(int index) {
        By ticketState = By.xpath(XPATH + index + pageElements.ticketState);
        final String text = getText(ticketState);
        commonLib.info("Reading Ticket state: " + text);
        return text;
    }

    /**
     * This method is use to get ticket close date based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getCloseDate(int index) {
        By ticketCloseDate = By.xpath(XPATH + index + pageElements.closeDate);
        final String text = getText(ticketCloseDate);
        commonLib.info("Reading Ticket close date: " + text);
        return text;
    }

    /**
     * This method is use to get ticket close time based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getCloseTime(int index) {
        By ticketCloseTime = By.xpath(XPATH + index + pageElements.closeTime);
        final String text = getText(ticketCloseTime);
        commonLib.info("Reading Ticket close time: " + text);
        return text;
    }

    /**
     * This method is use to get ticket created by based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getCreatedBy(int index) {
        By ticketCreatedBy = By.xpath(XPATH + index + pageElements.ticketCreatedBy);
        final String text = getText(ticketCreatedBy);
        commonLib.info("Reading Ticket created by: " + text);
        return text;
    }

    /**
     * This method is use to get ticket created date based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getCreationDate(int index) {
        By ticketCreationDate = By.xpath(XPATH + index + pageElements.ticketCreatedDate);
        final String text = getText(ticketCreationDate);
        commonLib.info("Reading Ticket Creation date: " + text);
        return text;
    }

    /**
     * This method is use to get ticket Creation time based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getCreationTime(int index) {
        By ticketCreationTime = By.xpath(XPATH + index + pageElements.ticketCreatedTime);
        final String text = getText(ticketCreationTime);
        commonLib.info("Reading Ticket Creation time: " + text);
        return text;
    }

    /**
     * This method is use to click add to interaction icon based on index
     *
     * @param index The Index
     */
    public void clickAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath(XPATH + index + pageElements.addToInteraction);
        commonLib.info("Clicking on Add to interaction button");
        clickAndWaitForLoaderToBeRemoved(addToInteractionBtn);
    }

    /**
     * This method is use to check add to interaction icon displayed or not based on index
     *
     * @param index The Index
     * @return true/false
     */
    public boolean checkAddToInteraction(int index) {
        By addToInteractionBtn = By.xpath(XPATH + index + pageElements.addToInteraction);
        String ticketId = getTicketId(index);
        commonLib.info("Checking Add to interaction on ticket id: " + ticketId);
        return isEnabled(addToInteractionBtn);
    }

    /**
     * This method is use to check reopen icon displayed or not based on index
     *
     * @param index The Index
     * @return true/false
     */
    public boolean checkReopen(int index) {
        By reopenBtn = By.xpath(XPATH + index + pageElements.reOpenIcon);
        commonLib.info("Checking reopen button on ticket");
        return isEnabled(reopenBtn);
    }

    /**
     * This method use to click reopen icon based on index
     *
     * @param index The index
     */
    public void clickReopen(int index) {
        By reopenBtn = By.xpath(XPATH + index + pageElements.reOpenIcon);
        commonLib.info("Clicking on Add to interaction button");
        clickAndWaitForLoaderToBeRemoved(reopenBtn);
    }

    /**
     * This method is use to get add to interaction icon based on index
     *
     * @param index The Index
     * @return String The value
     */
    public String getSourceApp(int index) {
        By check = By.xpath(XPATH + index + pageElements.ticketSourceApp);
        final String text = getText(check);
        commonLib.info("Reading Source APP: " + text);
        return text;
    }


    /**
     * This method is use to write ticket id into search box
     *
     * @param id The ticket id
     */
    public void writeTicketId(String id) {
        commonLib.info("Writing Ticket Id:" + id);
        enterText(pageElements.searchBox, id);
    }

    /**
     * This method is use to click search button
     */
    public void clickSearchBtn() {
        commonLib.info("Clicking Search button");
        clickAndWaitForLoaderToBeRemoved(pageElements.searchBtn);
    }

    /**
     * This method is used to check no ticket found icon displayed or not
     *
     * @return true/false
     */
    public boolean checkNoTicketFound() {
        commonLib.info("Checking Ticket Found or not");
        return isVisibleContinueExecution(pageElements.noTicketFound, 3);
    }

    /**
     * This method is used to click clear search box icon
     */
    public void clickClearSearchBox() {
        commonLib.info("Click on clear search box button");
        clickAndWaitForLoaderToBeRemoved(pageElements.clearTicketId);
    }

    /**
     * This method use to validate add to interaction icon displayed on each ticket present on page
     *
     * @return true/false
     */
    public boolean validateAddToInteractionIcon() {
        boolean result = false;
        try {
            commonLib.info("Checking Add To Interaction present or not");
            result = isVisible(pageElements.addToInteractionIcon);
            commonLib.pass("Add to interaction icon present on tickets");
        } catch (Exception e) {
            commonLib.error("Add to interaction icon is not present");
        }
        return result;
    }

    /**
     * This method is use to open ticket history tab
     */
    public void clickToOpenTicketTicketHistory() {
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketRow);
    }

    /**
     * This method is use to check Ticket history error visible when api not able to fetch data
     * @return true/false
     */
    public boolean isUnableToFetch() {
        final boolean elementVisible = isElementVisible(pageElements.unableToFetch);
        commonLib.info(constants.getValue("ticket.history.unabletofetch") + ":" + elementVisible);
        return elementVisible;
    }
}