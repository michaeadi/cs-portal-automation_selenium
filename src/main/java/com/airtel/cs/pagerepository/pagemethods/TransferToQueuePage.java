package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.commonutils.UtilsMethods;
import com.airtel.cs.pagerepository.pageelements.TransferToQueuePageElements;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class TransferToQueuePage extends BasePage {


    TransferToQueuePageElements pageElements;

    public TransferToQueuePage(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TransferToQueuePageElements.class);
    }

    public boolean validatePageTitle() {
        UtilsMethods.printInfoLog("Validating Transfer to Queue Title");
        return checkState(pageElements.pageTitle);
    }

    public void clickTransferQueue(String queueName) {
        log.info("Clicking on Transfer to Button");
        pageElements.transferQueue = By.xpath("//span[contains(text(),'" + queueName + "')]//ancestor::div[1]//following-sibling::div/img");
        click(pageElements.transferQueue);
        UtilsMethods.printInfoLog("Transferring Ticket to Ticket Pool Name: " + queueName);
    }

    public void clickCloseTab() {
        UtilsMethods.printInfoLog("Closing Transfer to Queue Tab");
        click(pageElements.closeTab);
    }
}
