package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.ViewHistoryPageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class ViewHistoryPage extends BasePage {

    ViewHistoryPageElements pageElements;

    public ViewHistoryPage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, ViewHistoryPageElements.class);
    }

    public void clickOnInteractionsTab() {
        log.info("Clicking on Interactions Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(pageElements.interactionsTab);
    }

    /*
    This Method will give you the row(Row May be interaction,issue or message count under view history) count
     */
    public Integer getRowCount() {
        final String paginationNumber = getText(pageElements.paginationDetails);
        final int length = paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).length();
        return Integer.parseInt(paginationNumber.substring(0, paginationNumber.lastIndexOf(" ")).substring(10, length));
    }

    public FrontendTicketHistoryPage goToTicketHistoryTab() {
        log.info("Clicking on Ticket History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(pageElements.ticketHistory);
        return new FrontendTicketHistoryPage(driver);
    }

    public MessageHistoryPage clickOnMessageHistory() {
        UtilsMethods.printInfoLog("Clicking on Message History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(pageElements.messageHistory);
        return new MessageHistoryPage(driver);
    }

    public ActionTrailPage clickOnActionTrailHistory() {
        UtilsMethods.printInfoLog("Clicking on Action Trail History Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(pageElements.actionTrailTab);
        return new ActionTrailPage(driver);
    }

    public String getLastCreatedIssueCode() {
        UtilsMethods.printInfoLog("Getting the issue code of last created FTR interaction ");
        waitTillLoaderGetsRemoved();
        return getText(pageElements.firstIssueCode);
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
            List<WebElement> list = returnListOfElement(pageElements.allIssue);
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
        return checkState(pageElements.ticketPageTitle);
    }

    public void clickCloseTicketTab() {
        log.info("closing ticket tab");
        click(pageElements.closeTicketTab);
    }

    /*
    This Method will route you to Action Trail Tab when You are under View History Tab
     */
    public void goToActionTrailTab() {
        if (isVisible(pageElements.actionTrailTab)) {
            click(pageElements.actionTrailTab);
        }
    }

    /*
    This Method will check Source Title is visible or not under NFTR details page
     */
    public Boolean isSourceAppVisible() {
        return isVisible(pageElements.sourceApp);
    }

    /*
    This Method will give us the value of Source App for a NFTR ticket under NFTR detail page
     */
    public String getSourceText() {
        return getText(pageElements.sourceAppValue);
    }
}
