package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.AWTException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TicketBulkUpdatePOM extends BasePage {

    By uploadFile = By.xpath("//input[@id='file']");
    By downloadFile = By.xpath("//a[@class=\"download-template\"]");
    By errorMessage = By.xpath("//div[@class=\"error-container\"]");
    By pageTitle = By.xpath("//span[contains(text(),'Bulk Update')]");
    By selectFilter = By.xpath("//span[@class=\"filter\"]");
    By ticketList=By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"]");
    By maxSelectMessage=By.xpath("//div[@class=\"negative-applied-filter ng-star-inserted\"]//p");
    By clearFilter=By.xpath("//button[@class=\"mat-button\"]//span[contains(text(),'Clear Filter')]");
    By nextBtn=By.xpath("//button[@class=\"next\"]");
    By cancelBtn=By.xpath("//button[@class=\"cancel\"]");
    By transferToQueue=By.xpath("//form//div//div[1]//div[@class=\"label customer-checkbox\"]//label");
    By selectTransferToQueue=By.xpath("//form//div[@class=\"choose-operation__form-section--form--input ng-star-inserted\"][1]//mat-form-field//mat-select");
    By selectChangeState=By.xpath("//form//div[@class=\"choose-operation__form-section--form--input ng-star-inserted\"][2]//mat-form-field//mat-select");
    By changeState=By.xpath("//form//div//div[2]//div[@class=\"label customer-checkbox\"]//label");
    By ticketComment=By.xpath("//form//div//div[3]//div[@class=\"label customer-checkbox\"]//label");
    By backBtn=By.xpath("//button[contains(text(),'BACK')]");
    By options=By.xpath("//span[@class=\"mat-option-text\"]");
    By popUpCancelBtn=By.xpath("//div[@class=\"deactivate-popup__button-section mat-dialog-actions\"]//button[1]");
    By popUpContinueBtn=By.xpath("//div[@class=\"deactivate-popup__button-section mat-dialog-actions\"]//button[2]");
    By commentBox=By.xpath("//input[@type=\"textarea\"]");
    By confirmAction=By.xpath("//div[@class=\"tnc customer-checkbox ng-star-inserted\"]//input");
    By statueBar=By.xpath("//span[@class=\"bar-fill-stripes\"]");
    By updateMessage=By.xpath("//div[@class=\"bar-status\"]");
    By successTicketId=By.xpath("class=\"id-section successful\"");
    By errorTicketId=By.xpath("//li[@class=\"id-section error-bg\"]//span[2]");
    By errorTicketMessage=By.xpath("//div[@class=\"bar-status\"]//span");

    public TicketBulkUpdatePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isTicketBulkUpdate() {
        printInfoLog("Checking Ticket Bulk Update page opened" + checkState(pageTitle));
        return checkState(pageTitle);
    }

    public boolean isSelectFilter(){
        printInfoLog("Checking select filter option available");
        return checkState(selectFilter);
    }

    public String getTransferToQueueOption(){
        printInfoLog("Option: "+readText(transferToQueue));
        return readText(transferToQueue).trim();
    }

    public String getChangeStateOption(){
        printInfoLog("Option: "+readText(changeState));
        return readText(changeState).trim();
    }

    public String getTicketCommentOption(){
        printInfoLog("Option: "+readText(ticketComment));
        return readText(ticketComment).trim();
    }

    public void clickNextBtn(){
        printInfoLog("Clicking on next button");
        click(nextBtn);
    }

    public void clickBackBtn(){
        printInfoLog("Clicking on back button");
        click(backBtn);
    }

    public void clickPopUpCancelBtn(){
        printInfoLog("Clicking on pop up cancel button");
        click(popUpCancelBtn);
    }

    public void clickPopUpContinueBtn(){
        printInfoLog("Clicking on pop up continue button");
        click(popUpContinueBtn);
    }

    public boolean isNextBtnEnable(){
        printInfoLog("Checking next button enable");
        return driver.findElement(nextBtn).isEnabled();
    }

    public void clickCancelBtn(){
        printInfoLog("Clicking on cancel button");
        click(cancelBtn);
    }

    public void addFile() {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, config.getProperty("ticketBulkUpdate"));
        String path = Excel.getAbsolutePath();
        printInfoLog("File adding:"+path);
        WebElement addFile = driver.findElement(uploadFile);
        addFile.sendKeys(path);
    }

    public boolean fileDownload() throws AWTException, InterruptedException {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, config.getProperty("ticketBulkUpdate"));
        if(!Excel.exists()) {
            printInfoLog("Downloading Template File");
            WebElement btnDownload = driver.findElement(downloadFile);
            btnDownload.click();
            Thread.sleep(7000);
        }
        return Excel.canRead();
    }

    public String getErrorMessage() {
        printInfoLog("Reading Error Message: " + readText(errorMessage).trim());
        return readText(errorMessage).trim();
    }

    public String getMaxSelectMessage() {
        printInfoLog("Reading Max select Message: " + readText(maxSelectMessage).trim());
        return readText(maxSelectMessage).trim();
    }

    public FilterTabPOM clickSelectFilter(){
        printInfoLog("Clicking selecting filter");
        click(selectFilter);
        return new FilterTabPOM(driver);
    }

    public void clickClearFilter(){
        printInfoLog("Clicking clear filter");
        click(clearFilter);
    }

    public boolean deleteFile(){
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "BulkUploadTemplate.xlsx");
        if(Excel.delete()){
            printInfoLog("File Deleted ");
            return true;
        }else {
            printInfoLog("Not able to delete Excel");
            return false;
        }
    }

    public List<String> getTicketList(){
        List<String> tickets=new ArrayList<>();
        List<WebElement> list=returnListOfElement(ticketList);
        for(int i=1;i<=list.size();i++){
            By ticketNumber=By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"]["+i+"]//ul[1]//li[1]//span[2]");
            tickets.add(readText(ticketNumber));
        }
        return tickets;
    }

    public List<String> getQueue(){
        List<String> queues=new ArrayList<>();
        click(transferToQueue);
        click(selectTransferToQueue);
        List<WebElement> list=returnListOfElement(options);
        for(int i=1;i<=list.size();i++){
            By queue=By.xpath("//mat-option["+i+"]//span");
            queues.add(readText(queue).trim().toLowerCase());
        }
        clickOutside();
        clickTransferToQueueOption();
        return queues;
    }

    public List<String> getState(){
        List<String> states=new ArrayList<>();
        click(changeState);
        click(selectChangeState);
        List<WebElement> list=returnListOfElement(options);
        for(int i=1;i<=list.size();i++){
            By state=By.xpath("//mat-option["+i+"]//span");
            states.add(readText(state).trim().toLowerCase());
        }
        clickOutside();
        clickTicketStateOption();
        return states;
    }

    public void clickAddCommentOption(){
        printInfoLog("Clicking Add Ticket Comment Option");
        click(ticketComment);
    }

    public void clickTransferToQueueOption(){
        printInfoLog("Clicking Transfer to Queue Option");
        click(transferToQueue);
    }

    public void clickTicketStateOption(){
        printInfoLog("Clicking Ticket State Option");
        click(changeState);
    }

    public void addComment(String comment){
        printInfoLog("Adding comment: "+comment);
        writeText(commentBox,comment);
    }

    public void clickConfirmAction(){
        printInfoLog("Clicking Confirm option");
        click(confirmAction);
    }

    public boolean isStatusBarComplete(){
        printInfoLog("Waiting for Status to be complete");
        return checkState(statueBar);
    }

    public String getUpdatedMessage(){
        printInfoLog("Message After Ticket Action Performed: "+readText(updateMessage));
        return readText(updateMessage);
    }

    public String getSuccessCount(){
        List<WebElement> list=returnListOfElement(successTicketId);
        return String.valueOf(list.size());
    }

    public String getErrorCount(){
        List<WebElement> list=returnListOfElement(errorTicketId);
        return String.valueOf(list.size());
    }

    public int getErrorTicketCount(){
        if(Integer.parseInt(getErrorCount())>0){
            String text=readText(errorTicketMessage).trim().replaceAll("^[0-9]","");
            return Integer.parseInt(text);
        }
        return 0;
    }

}
