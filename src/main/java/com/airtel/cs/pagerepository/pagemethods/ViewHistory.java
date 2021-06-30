package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.ViewHistoryPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class ViewHistory extends BasePage {

    ViewHistoryPage pageElements;

    public ViewHistory(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ViewHistoryPage.class);
    }

    /**
     * This method use to click on interaction tab under view history tab
     */
    public void clickOnInteractionsTab() {
        commonLib.info("Clicking on Interactions Tab under view history ");
        clickAndWaitForLoaderToBeRemoved(pageElements.interactionsTab);
    }

    /*
    This Method will give you the row(Row May be interaction,issue or message count under view history) count
     */
    public Integer getRowCount() {
        final String paginationNumber = getText(pageElements.paginationDetails);
        final int length = paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).length();
        return Integer.parseInt(paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).substring(10, length));
    }

    /**
     * This method use to click on ticket history tab under view history tab
     */
    public void goToTicketHistoryTab() {
        commonLib.info("Clicking on Ticket History Tab under view history ");
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketHistory);
    }

    /*
   With this Method we will route to the Message History tab under view history tab
    */
    public void clickOnMessageHistory() {
        if (isVisible(pageElements.messageHistory)) {
            commonLib.info("Clicking on Message History Tab under view history");
            clickAndWaitForLoaderToBeRemoved(pageElements.messageHistory);
        } else {
            commonLib.fail("Exception in method - clickOnMessageHistory ", true);
        }
    }

    /**
     * This method use to click on action trail history tab under view history tab
     */
    public void clickOnActionTrailHistory() {
        if (isVisible(pageElements.actionTrailTab)) {
            commonLib.info("Clicking on Action Trail History Tab under view history");
            clickAndWaitForLoaderToBeRemoved(pageElements.actionTrailTab);
        } else {
            commonLib.fail("Exception in method - clickOnActionTrailHistory ", true);
        }
    }

    /**
     * This method is use to get last created issue code under interaction tab
     *
     * @return String The value
     */
    public String getLastCreatedIssueCode() {
        commonLib.info("Getting the issue code of last created FTR interaction ");
        return getText(pageElements.firstIssueCode);
    }

    /**
     * This method is use to get ftr issue value based on ticket row number
     *
     * @param index The row number
     * @return String The value
     */
    public String ftrIssueValue(int index) {
        By element = By.xpath(pageElements.ticketRows + index + pageElements.ticketIcon);
        String value = driver.findElement(element).getAttribute("title");
        commonLib.info("Reading Attribute Value: " + value);
        return value;
    }

    /**
     * This method is use to get ticket id based on ticket row number
     *
     * @param index The row number
     * @return String The value
     */
    public String nftrIssueValue(int index) {
        By element = By.xpath(pageElements.ticketRows + index + pageElements.ticketIcon);
        String value = driver.findElement(element).getAttribute("title");
        commonLib.info("Reading Attribute Value: " + value);
        return value;
    }


    /**
     * This method is use to click ticket icon based on row number
     *
     * @param index The row number
     */
    public void clickTicketIcon(int index) {
        By element = By.xpath(pageElements.ticketRows + index + pageElements.ticketIcon);
        commonLib.info("Clicking on ticket icon");
        clickAndWaitForLoaderToBeRemoved(element);
    }

    /**
     * This method is use to click on first ticket icon found in ticket list or not
     *
     * @return true/false
     */
    public boolean clickOnTicketIcon() {
        try {
            List<WebElement> list = returnListOfElement(pageElements.allIssue);
            for (int i = 1; i <= list.size(); i++) {
                if (!nftrIssueValue(i).equalsIgnoreCase("ftr")) {
                    commonLib.info("Clicking on Ticket NFTR ticket icon" + nftrIssueValue(i));
                    clickTicketIcon(i);
                    waitTillLoaderGetsRemoved();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            commonLib.info("Something went wrong");
        }
        commonLib.warning("No any NFTR issue found");
        return false;
    }

    /**
     * This method use to check view ticket page displayed or not
     *
     * @return true/false
     */
    public boolean checkViewTicketPage() {
        commonLib.info("Checking View Ticket Page");
        return isEnabled(pageElements.ticketPageTitle);
    }

    /**
     * This method is use to click close ticket tab
     */
    public void clickCloseTicketTab() {
        commonLib.info("closing ticket tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTicketTab);
    }

    /*
    This Method will route you to Action Trail Tab when You are under View History Tab
     */
    public void goToActionTrailTab() {
        if (isVisible(pageElements.actionTrailTab)) {
            clickAndWaitForLoaderToBeRemoved(pageElements.actionTrailTab);
        }
    }

    /*
    This Method will check Source Title is visible or not under NFTR details page
     */
    public Boolean isSourceAppVisible() {
        boolean result = false;
        result = isVisible(pageElements.sourceApp);
        highLighterMethod(pageElements.sourceApp);
        return result;
    }

    /*
    This Method will give us the value of Source App for a NFTR ticket under NFTR detail page
     */
    public String getSourceText() {
        return getText(pageElements.sourceAppValue);
    }

    /*
    This Method will close the Interaction History Detail page
     */
    public void closeInteractionHistoryDetailPage() {
        clickAndWaitForLoaderToBeRemoved(pageElements.closeBtn);
    }
}
