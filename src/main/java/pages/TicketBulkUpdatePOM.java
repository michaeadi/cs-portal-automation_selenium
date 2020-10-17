package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.io.File;

public class TicketBulkUpdatePOM extends BasePage {

    By uploadFile = By.xpath("//input[@id='file']");
    By downloadFile = By.xpath("//a[@class=\"download-template\"]");
    By errorMessage = By.xpath("//div[@class=\"error-container\"]");
    By pageTitle = By.xpath("//span[contains(text(),'Bulk Update')]");
    By selectFilter = By.xpath("//span[@class=\"filter\"]");

    public TicketBulkUpdatePOM(WebDriver driver) {
        super(driver);
    }

    public boolean isTicketBulkUpdate() {
        printInfoLog("Checking Ticket Bulk Update page opened" + checkState(pageTitle));
        return checkState(pageTitle);
    }

    public void addFile() {
        File Exceldir = new File("Excels");
        File Excel = new File(Exceldir, "BulkUploadTemplate.xlsx");
        String path = Excel.getAbsolutePath();
        printInfoLog(path);
        WebElement addFile = driver.findElement(uploadFile);
        addFile.sendKeys(path);
    }

    public void fileDownload() throws AWTException, InterruptedException {
        WebElement btnDownload = driver.findElement(downloadFile);
        btnDownload.click();
        Thread.sleep(7000);

    }

    public void getErrorMessage() {
        printFailLog("Reading Error Message: " + readText(errorMessage));
    }

    public void clickSelectFilter(){
        printInfoLog("Clicking selecting filter");
        click(selectFilter);
    }
}
