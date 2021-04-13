package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.TicketBulkUpdatePageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.awt.AWTException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketBulkUpdatePage extends BasePage {


    TicketBulkUpdatePageElements pageElements;

    public TicketBulkUpdatePage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TicketBulkUpdatePageElements.class);
    }

    public boolean isTicketBulkUpdate() {
        UtilsMethods.printInfoLog("Checking Ticket Bulk Update page opened: " + checkState(pageElements.pageTitle));
        return checkState(pageElements.pageTitle);
    }

    public void clickCloseFilter() {
        UtilsMethods.printInfoLog("Closing Filter Tab");
        click(pageElements.closeFilter);
    }
    public boolean isSelectFilter() {
        UtilsMethods.printInfoLog("Checking select filter option available");
        return checkState(pageElements.selectFilter);
    }

    public String getTransferToQueueOption() {
        UtilsMethods.printInfoLog("Option: " + getText(pageElements.transferToQueue));
        return getText(pageElements.transferToQueue).trim();
    }

    public String getChangeStateOption() {
        UtilsMethods.printInfoLog("Option: " + getText(pageElements.changeState));
        return getText(pageElements.changeState).trim();
    }

    public String getTicketCommentOption() {
        UtilsMethods.printInfoLog("Option: " + getText(pageElements.ticketComment));
        return getText(pageElements.ticketComment).trim();
    }

    public void clickNextBtn() {
        UtilsMethods.printInfoLog("Clicking on next button");
        click(pageElements.nextBtn);
    }

    public void clickBackBtn() {
        UtilsMethods.printInfoLog("Clicking on back button");
        click(pageElements.backBtn);
    }

    public void clickPopUpCancelBtn() {
        UtilsMethods.printInfoLog("Clicking on pop up cancel button");
        click(pageElements.popUpCancelBtn);
    }

    public void clickPopUpContinueBtn() {
        UtilsMethods.printInfoLog("Clicking on pop up continue button");
        click(pageElements.popUpContinueBtn);
    }

    public boolean isNextBtnEnable() {
        UtilsMethods.printInfoLog("Checking next button enable");
        return driver.findElement(pageElements.nextBtn).isEnabled();
    }

    public void clickCancelBtn() {
        UtilsMethods.printInfoLog("Clicking on cancel button");
        click(pageElements.cancelBtn);
    }

    public void addFile() {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, config.getProperty("ticketBulkUpdate"));
        String path = Excel.getAbsolutePath();
        UtilsMethods.printInfoLog("File adding:" + path);
        WebElement addFile = driver.findElement(pageElements.uploadFile);
        addFile.sendKeys(path);
    }

    public boolean fileDownload() throws AWTException, InterruptedException {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, config.getProperty("ticketBulkUpdate"));
        if (!Excel.exists()) {
            UtilsMethods.printInfoLog("Downloading Template File");
            WebElement btnDownload = driver.findElement(pageElements.downloadFile);
            btnDownload.click();
            Thread.sleep(7000);
        }
        return Excel.canRead();
    }

    public String getErrorMessage() {
        UtilsMethods.printInfoLog("Reading Error Message: " + getText(pageElements.errorMessage).trim());
        return getText(pageElements.errorMessage).trim();
    }

    public String getMaxSelectMessage() {
        UtilsMethods.printInfoLog("Reading Max select Message: " + getText(pageElements.maxSelectMessage).trim());
        return getText(pageElements.maxSelectMessage).trim();
    }

    public FilterTabPage clickSelectFilter() {
        UtilsMethods.printInfoLog("Clicking selecting filter");
        click(pageElements.selectFilter);
        return new FilterTabPage(driver);
    }

    public void clickClearFilter() {
        UtilsMethods.printInfoLog("Clicking clear filter");
        click(pageElements.clearFilter);
    }

    public Boolean isClearFilterButton() {
        UtilsMethods.printInfoLog("Checking Clear Filter Button Display");
        return checkState(pageElements.clearFilter);
    }

    public boolean deleteFile() {
        File excelDir = new File("Excels");
        File excel = new File(excelDir, "BulkUploadTemplate.xlsx");
        if (excel.delete()) {
            UtilsMethods.printInfoLog("File Deleted ");
            return true;
        } else {
            UtilsMethods.printInfoLog("Not able to delete Excel");
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
        UtilsMethods.printInfoLog("Clicking Add Ticket Comment Option");
        click(pageElements.ticketComment);
    }

    public void clickTransferToQueueOption() {
        UtilsMethods.printInfoLog("Clicking Transfer to Queue Option");
        click(pageElements.transferToQueue);
    }

    public void clickTicketStateOption() {
        UtilsMethods.printInfoLog("Clicking Ticket State Option");
        click(pageElements.changeState);
    }

    public void addComment(String comment) {
        UtilsMethods.printInfoLog("Adding comment: " + comment);
        writeText(pageElements.commentBox, comment);
    }

    public void clickConfirmAction() {
        UtilsMethods.printInfoLog("Clicking Confirm option");
        click(pageElements.confirmAction);
    }

    public boolean isStatusBarComplete() {
        UtilsMethods.printInfoLog("Waiting for Status to be complete");
        return checkState(pageElements.statueBar);
    }

    public String getUpdatedMessage() {
        UtilsMethods.printInfoLog("Message After Ticket Action Performed: " + getText(pageElements.updateMessage));
        return getText(pageElements.updateMessage);
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
