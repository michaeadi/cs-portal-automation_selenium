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

    public TicketBulkUpdatePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isTicketBulkUpdate() {
        printInfoLog("Checking Ticket Bulk Update page opened" + checkState(pageTitle));
        return checkState(pageTitle);
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
        WebElement btnDownload = driver.findElement(downloadFile);
        btnDownload.click();
        Thread.sleep(7000);
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, config.getProperty("ticketBulkUpdate"));
        return Excel.canRead();
    }

    public void getErrorMessage() {
        printFailLog("Reading Error Message: " + readText(errorMessage));
    }

    public void clickSelectFilter(){
        printInfoLog("Clicking selecting filter");
        click(selectFilter);
    }

    public void deleteFile(){
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "BulkUploadTemplate.xlsx");
        if(Excel.delete()){
            printInfoLog("File Deleted ");
        }else {
            printInfoLog("Not able to delete Excel");
        }
    }

    public List<String> getTicketList(){
        List<String> tickets=new ArrayList<>();
        List<WebElement> list=driver.findElements(ticketList);
        for(int i=1;i<=list.size();i++){
            By ticketNumber=By.xpath("//div[@class=\"container-fluid table-card ng-star-inserted\"]["+i+"]//ul[1]//li[1]//span[2]");
            tickets.add(readText(ticketNumber));
        }
        return tickets;
    }
}
