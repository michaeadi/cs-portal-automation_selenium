package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.TransferToQueuePage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class TransferToQueue extends BasePage {


    TransferToQueuePage pageElements;

    public TransferToQueue(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, TransferToQueuePage.class);
    }

    public boolean validatePageTitle() {
        commonLib.info("Validating Transfer to Queue Title");
        return isEnabled(pageElements.pageTitle);
    }

    public void clickTransferQueue(String queueName) {
        log.info("Clicking on Transfer to Button");
        pageElements.transferQueue = By.xpath("//span[contains(text(),'" + queueName + "')]//ancestor::div[1]//following-sibling::div/img");
        clickAndWaitForLoaderToBeRemoved(pageElements.transferQueue);
        commonLib.info("Transferring Ticket to Ticket Pool Name: " + queueName);
    }

    public void clickCloseTab() {
        commonLib.info("Closing Transfer to Queue Tab");
        clickAndWaitForLoaderToBeRemoved(pageElements.closeTab);
    }

    /**
     * This method use to get first queue name from transfer to queue tab
     * @return String Queue Name
     * */
    public String getFirstTransferQueue() {
        commonLib.info("Getting first queue name");
        return getText(pageElements.firstQueueName);
    }
}
