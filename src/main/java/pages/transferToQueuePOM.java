package pages;

import Utils.ExtentReports.ExtentTestManager;
import Utils.UtilsMethods;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class transferToQueuePOM extends BasePage {


    By pageTitle = By.xpath("//*[@id=\"assignQueue\"]/app-assign-to-queue/section/div/div[1]/h4");
    By transferQueue;
    By closeTab=By.xpath("//button[@class=\"mat-button\"]//span[contains(text(),'X')]");

    public transferToQueuePOM(WebDriver driver) {
        super(driver);
    }

    public boolean validatePageTitle() {
        UtilsMethods.printInfoLog("Validating Transfer to Queue Title");
        return checkState(pageTitle);
    }

    public void clickTransferQueue(String queueName) {
        log.info("Clicking on Transfer to Button");
        transferQueue = By.xpath("//span[contains(text(),'" + queueName + "')]//ancestor::div[1]//following-sibling::div/img");
        click(transferQueue);
        UtilsMethods.printInfoLog("Transferring Ticket to Ticket Pool Name: " + queueName);
    }

    public void clickCloseTab(){
        UtilsMethods.printInfoLog("Closing Transfer to Queue Tab");
        click(closeTab);
    }
}
