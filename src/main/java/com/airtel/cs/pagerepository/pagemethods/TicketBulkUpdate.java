package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.applicationutils.constants.CommonConstants;
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

    /**
     * This method is use to check ticket bulk update page display or not
     * @return true/false
     */
    public boolean isTicketBulkUpdate() {
        final boolean state = isEnabled(pageElements.pageTitle);
        commonLib.info("Checking Ticket Bulk Update page opened: " + state);
        return state;
    }

    /**
     * This method is use to click close filter button
     */
    public void clickCloseFilter() {
        commonLib.info("Closing Filter Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeFilter);
    }

    /**
     * This method is use to check select filter option available or not
     * @return true/false
     */
    public boolean isSelectFilter() {
        commonLib.info("Checking select filter option available");
        return isEnabled(pageElements.selectFilter);
    }

    /**
     * This method use to get transfer to queue Action name
     * @return String The value
     */
    public String getTransferToQueueOption() {
        final String text = getText(pageElements.transferToQueue);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    /**
     * This method use to get change state Action name
     * @return String The value
     */
    public String getChangeStateOption() {
        final String text = getText(pageElements.changeState);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    /**
     * This method use to get add ticket comment Action name
     * @return String The value
     */
    public String getTicketCommentOption() {
        final String text = getText(pageElements.ticketComment);
        commonLib.info(OPTION + text);
        return text.trim();
    }

    /**
     * This method is use to click next button
     */
    public void clickNextBtn() {
        commonLib.info("Clicking on next button");
        clickAndWaitForLoaderToBeRemoved(pageElements.nextBtn);
    }

    /**
     * This method is use to click back button
     */
    public void clickBackBtn() {
        commonLib.info("Clicking on back button");
        clickAndWaitForLoaderToBeRemoved(pageElements.backBtn);
    }

    /**
     * This method is use to click cancel button over pop up window
     */
    public void clickPopUpCancelBtn() {
        commonLib.info("Clicking on pop up cancel button");
        clickAndWaitForLoaderToBeRemoved(pageElements.popUpCancelBtn);
    }

    /**
     * This method is use to click continue button over pop up window
     */
    public void clickPopUpContinueBtn() {
        commonLib.info("Clicking on pop up continue button");
        clickAndWaitForLoaderToBeRemoved(pageElements.popUpContinueBtn);
    }

    /**
     * This method is use to check next button enable or not
     * @return true/false
     */
    public boolean isNextBtnEnable() {
        commonLib.info("Checking next button enable");
        return driver.findElement(pageElements.nextBtn).isEnabled();
    }

    /**
     * This method is use to click cancel button
     */
    public void clickCancelBtn() {
        commonLib.info("Clicking on cancel button");
        clickAndWaitForLoaderToBeRemoved(pageElements.cancelBtn);
    }

    /**
     * This method use to upload  excel file to upload excel field present on UI
     */
    public void addFile() {
        String excelPath=download+constants.getValue(CommonConstants.TICKET_BULK_UPDATE_SHEET);
        commonLib.info("File adding:" + excelPath);
        WebElement addFile = driver.findElement(pageElements.uploadFile);
        addFile.sendKeys(excelPath);
    }

    /**
     * This method is use to download the file , file will download using download button present on Ui if does not already exist
     * @return true/false The file readable or not
     * @throws InterruptedException in-case download take more time
     */
    public boolean fileDownload() throws InterruptedException {
        File excelDir = new File(download);
        File excel = new File(excelDir, constants.getValue(CommonConstants.TICKET_BULK_UPDATE_SHEET));
        if (!excel.exists()) {
            commonLib.info("Downloading Template File");
            WebElement btnDownload = driver.findElement(pageElements.downloadFile);
            btnDownload.click();
            Thread.sleep(7000);
        }
        return excel.canRead();
    }

    /**
     * This method is use to get error message
     * @return String The value
     */
    public String getErrorMessage() {
        final String text = getText(pageElements.errorMessage);
        commonLib.info("Reading Error Message: " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to get max count error message
     * @return String The value
     */
    public String getMaxSelectMessage() {
        final String text = getText(pageElements.maxSelectMessage);
        commonLib.info("Reading Max select Message: " + text.trim());
        return text.trim();
    }

    /**
     * This method is use to click select filter button
     */
    public void clickSelectFilter() {
        commonLib.info("Clicking selecting filter");
        clickAndWaitForLoaderToBeRemoved(pageElements.selectFilter);
    }

    /**
     * This method is use to click clear filter button
     */
    public void clickClearFilter() {
        commonLib.info("Clicking clear filter");
        clickAndWaitForLoaderToBeRemoved(pageElements.clearFilter);
    }

    /**
     * This method is use to check clear filter button visible or not
     * @return true/false
     */
    public Boolean isClearFilterButton() {
        commonLib.info("Checking Clear Filter Button Display");
        return isEnabled(pageElements.clearFilter);
    }

    /**
     * This method is use to delete file and return the status file deleted or not
     * @return true/false
     */
    public boolean deleteFile() {
        File excelDir = new File(download);
        File excel = new File(excelDir, constants.getValue(CommonConstants.TICKET_BULK_UPDATE_SHEET));
        if (excel.delete()) {
            commonLib.info("File Deleted ");
            return true;
        } else {
            commonLib.info("Not able to delete Excel");
            return false;
        }
    }

    /**
     * This method is use to get all ticket ids
     * @return List The list of ticket id's
     */
    public List<String> getTicketList() {
        List<String> tickets = new ArrayList<>();
        List<WebElement> list = returnListOfElement(pageElements.ticketList);
        for (int i = 1; i <= list.size(); i++) {
            By ticketNumber = By.xpath(pageElements.ticketRows + i + pageElements.ticketIds);
            tickets.add(getText(ticketNumber));
        }
        return tickets;
    }

    /**
     * This method is use to get all queue ids
     * @return List The list of queue name
     */
    public List<String> getQueue() {
        List<String> queues = new ArrayList<>();
        clickAndWaitForLoaderToBeRemoved(pageElements.transferToQueue);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectTransferToQueue);
        List<WebElement> list = returnListOfElement(pageElements.options);
        for (int i = 1; i <= list.size(); i++) {
            By queue = By.xpath(pageElements.option + i + pageElements.getText);
            queues.add(getText(queue).trim().toLowerCase());
        }
        clickOutside();
        clickTransferToQueueOption();
        return queues;
    }

    /**
     * This method is use to click transfer to queue option
     */
    public void clickSelectTransferToQueue(){
        commonLib.info("Clicking on Select transfer to queue");
        clickAndWaitForLoaderToBeRemoved(pageElements.selectTransferToQueue);
    }

    /**
     * This method is use to select queue name based on index and get the queue name which selected
     * @param i The index
     * @return String The queue name
     */
    public String selectOptionAndGetQueueName(int i){
        By queue = By.xpath(pageElements.option + i + pageElements.getText);
        String text=getText(queue);
        commonLib.info("Selecting Option with Queue Name: "+text);
        clickWithoutLoader(queue);
        return text;
    }

    /**
     * This method is used to select option by name
     * @param text the name
     */
    public void selectOptionByName(String text){
        commonLib.info("Selecting ticket option");
        selectByText(text);
    }

    /**
     * This method is use to get all state name
     * @return List The list of state name
     */
    public List<String> getState() {
        List<String> states = new ArrayList<>();
        clickAndWaitForLoaderToBeRemoved(pageElements.changeState);
        clickAndWaitForLoaderToBeRemoved(pageElements.selectChangeState);
        List<WebElement> list = returnListOfElement(pageElements.options);
        for (int i = 1; i <= list.size(); i++) {
            By state = By.xpath(pageElements.option + i + pageElements.getText);
            states.add(getText(state).trim().toLowerCase());
        }
        clickOutside();
        clickTicketStateOption();
        return states;
    }

    /**
     * This method use to click add comment option
     */
    public void clickAddCommentOption() {
        commonLib.info("Clicking Add Ticket Comment Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.ticketComment);
    }

    /**
     * This method is use to open select state options
     */
    public void clickSelectStateOption(){
        commonLib.info("Clicking on Select state option");
        clickAndWaitForLoaderToBeRemoved(pageElements.selectChangeState);
    }

    /**
     * This method is use to open select transfer to queue options
     */
    public void clickTransferToQueueOption() {
        commonLib.info("Clicking Transfer to Queue Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.transferToQueue);
    }

    /**
     * This method is use to click ticket state options
     */
    public void clickTicketStateOption() {
        commonLib.info("Clicking Ticket State Option");
        clickAndWaitForLoaderToBeRemoved(pageElements.changeState);
    }

    /**
     * This method is use write comment into comment box
     * @param comment The comment box
     */
    public void addComment(String comment) {
        commonLib.info("Adding comment: " + comment);
        enterText(pageElements.commentBox, comment);
    }

    /**
     * This method is use to click confirm action action check box
     */
    public void clickConfirmAction() {
        commonLib.info("Clicking Confirm option");
        clickAndWaitForLoaderToBeRemoved(pageElements.confirmAction);
    }

    /**
     * This method is use to check status bar complete
     * @return true/false
     */
    public boolean isStatusBarComplete() {
        commonLib.info("Waiting for Status to be complete");
        return isEnabled(pageElements.statueBar);
    }

    /**
     * This method is use to get message after action performed
     * @return String The message
     */
    public String getUpdatedMessage() {
        final String text = getText(pageElements.updateMessage);
        commonLib.info("Message After Ticket Action Performed: " + text);
        return text;
    }

    /**
     * This method is use to get success count of ticket updated
     * @return Integer The count
     */
    public Integer getSuccessCount() {
        List<WebElement> list = returnListOfElement(pageElements.successTicketId);
        return list.size();
    }

    /**
     * This method is use to get error count of ticket not updated
     * @return Integer The count
     */
    public String getErrorCount() {
        List<WebElement> list = returnListOfElement(pageElements.errorTicketId);
        return String.valueOf(list.size());
    }

    /**
     * This method is use to get count of error icon displayed and ticket not updated
     * @return Integer The count
     */
    public int getErrorTicketCount() {
        if (Integer.parseInt(getErrorCount()) > 0) {
            String text = getText(pageElements.errorTicketMessage).trim().replaceAll("^[0-9]", "");
            return Integer.parseInt(text);
        }
        return 0;
    }

    /**
     * This method is use to click ok button
     */
    public void clickOkButton(){
        commonLib.info("Clicking on Ok Button");
        clickAndWaitForLoaderToBeRemoved(pageElements.okButton);
    }

    /*
   This Method will be used to check Source Title is visible or not under Bulk update tab Ticket Listing
    */
    public Boolean isSourceTitleVisible() {
        boolean result = false;
        result =isVisible(pageElements.sourceTitleTicketRowTicketListing);
        highLighterMethod(pageElements.sourceTitleTicketRowTicketListing);
        return result;
    }
}
