package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.TicketBulkUpdatePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketBulkUpdate extends BasePage {

    TicketBulkUpdatePage pageElements;
    private static final String OPTION = "Option: ";

    public TicketBulkUpdate(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TicketBulkUpdatePage.class);
    }

    public boolean isTicketBulkUpdate() {
        final boolean state = isEnabled(pageElements.pageTitle);
        commonLib.info("Checking Ticket Bulk Update page opened: " + state);
        return state;
    }

    public void clickCloseFilter() {
        commonLib.info("Closing Filter Tab");
        click(pageElements.closeFilter);
    }

    public boolean isSelectFilter() {
        commonLib.info("Checking select filter option available");
        return isEnabled(pageElements.selectFilter);
    }

    public String getTransferToQueueOption() {
        final String text = getText(pageElements.transferToQueue);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    public String getChangeStateOption() {
        final String text = getText(pageElements.changeState);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    public String getTicketCommentOption() {
        final String text = getText(pageElements.ticketComment);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    public void clickNextBtn() {
        commonLib.info("Clicking on next button");
        click(pageElements.nextBtn);
    }

    public void clickBackBtn() {
        commonLib.info("Clicking on back button");
        click(pageElements.backBtn);
    }

    public void clickPopUpCancelBtn() {
        commonLib.info("Clicking on pop up cancel button");
        click(pageElements.popUpCancelBtn);
    }

    public void clickPopUpContinueBtn() {
        commonLib.info("Clicking on pop up continue button");
        click(pageElements.popUpContinueBtn);
    }

    public boolean isNextBtnEnable() {
        commonLib.info("Checking next button enable");
        return driver.findElement(pageElements.nextBtn).isEnabled();
    }

    public void clickCancelBtn() {
        commonLib.info("Clicking on cancel button");
        click(pageElements.cancelBtn);
    }

    public void addFile() {
        commonLib.info("File adding:" + excelPath);
        WebElement addFile = driver.findElement(pageElements.uploadFile);
        addFile.sendKeys(excelPath);
    }

    public boolean fileDownload() throws InterruptedException {
        File excelDir = new File("excels");
        File excel = new File(excelDir, config.getProperty("ticketBulkUpdate"));
        if (!excel.exists()) {
            commonLib.info("Downloading Template File");
            WebElement btnDownload = driver.findElement(pageElements.downloadFile);
            btnDownload.click();
            Thread.sleep(7000);
        }
        return excel.canRead();
    }

    public String getErrorMessage() {
        final String text = getText(pageElements.errorMessage);
        commonLib.info("Reading Error Message: " + text.trim());
        return text.trim();
    }

    public String getMaxSelectMessage() {
        final String text = getText(pageElements.maxSelectMessage);
        commonLib.info("Reading Max select Message: " + text.trim());
        return text.trim();
    }

    public FilterTab clickSelectFilter() {
        commonLib.info("Clicking selecting filter");
        click(pageElements.selectFilter);
        return new FilterTab(driver);
    }

    public void clickClearFilter() {
        commonLib.info("Clicking clear filter");
        click(pageElements.clearFilter);
    }

    public Boolean isClearFilterButton() {
        commonLib.info("Checking Clear Filter Button Display");
        return isEnabled(pageElements.clearFilter);
    }

    public boolean deleteFile() {
        File excelDir = new File("resources/excels");
        File excel = new File(excelDir, "BulkUploadTemplate.xlsx");
        if (excel.delete()) {
            commonLib.info("File Deleted ");
            return true;
        } else {
            commonLib.info("Not able to delete Excel");
            return false;
        }
    }

    public List<String> getTicketList() {
        List<String> tickets = new ArrayList<>();
        List<WebElement> list = returnListOfElement(pageElements.ticketList);
        for (int i = 1; i <= list.size(); i++) {
            By ticketNumber = By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"][" + i + "]//ul[1]//li[1]//span[2]");
            tickets.add(getText(ticketNumber));
        }
        return tickets;
    }

    public List<String> getQueue() {
        List<String> queues = new ArrayList<>();
        click(pageElements.transferToQueue);
        click(pageElements.selectTransferToQueue);
        List<WebElement> list = returnListOfElement(pageElements.options);
        for (int i = 1; i <= list.size(); i++) {
            By queue = By.xpath("//mat-option[" + i + "]//span");
            queues.add(getText(queue).trim().toLowerCase());
        }
        clickOutside();
        clickTransferToQueueOption();
        return queues;
    }

    public List<String> getState() {
        List<String> states = new ArrayList<>();
        click(pageElements.changeState);
        click(pageElements.selectChangeState);
        List<WebElement> list = returnListOfElement(pageElements.options);
        for (int i = 1; i <= list.size(); i++) {
            By state = By.xpath("//mat-option[" + i + "]//span");
            states.add(getText(state).trim().toLowerCase());
        }
        clickOutside();
        clickTicketStateOption();
        return states;
    }

    public void clickAddCommentOption() {
        commonLib.info("Clicking Add Ticket Comment Option");
        click(pageElements.ticketComment);
    }

    public void clickTransferToQueueOption() {
        commonLib.info("Clicking Transfer to Queue Option");
        click(pageElements.transferToQueue);
    }

    public void clickTicketStateOption() {
        commonLib.info("Clicking Ticket State Option");
        click(pageElements.changeState);
    }

    public void addComment(String comment) {
        commonLib.info("Adding comment: " + comment);
        enterText(pageElements.commentBox, comment);
    }

    public void clickConfirmAction() {
        commonLib.info("Clicking Confirm option");
        click(pageElements.confirmAction);
    }

    public boolean isStatusBarComplete() {
        commonLib.info("Waiting for Status to be complete");
        return isEnabled(pageElements.statueBar);
    }

    public String getUpdatedMessage() {
        final String text = getText(pageElements.updateMessage);
        commonLib.info("Message After Ticket Action Performed: " + text);
        return text;
    }

    public Integer getSuccessCount() {
        List<WebElement> list = returnListOfElement(pageElements.successTicketId);
        return list.size();
    }

    public String getErrorCount() {
        List<WebElement> list = returnListOfElement(pageElements.errorTicketId);
        return String.valueOf(list.size());
    }

    public int getErrorTicketCount() {
        if (Integer.parseInt(getErrorCount()) > 0) {
            String text = getText(pageElements.errorTicketMessage).trim().replaceAll("^[0-9]", "");
            return Integer.parseInt(text);
        }
        return 0;
    }

}
