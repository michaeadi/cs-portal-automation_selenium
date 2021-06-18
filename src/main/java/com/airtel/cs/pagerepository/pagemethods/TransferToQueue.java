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

    /**
     * This method is use to validate transfer to queue tab title
     * @return true/false
     */
    public boolean validatePageTitle() {
        commonLib.info("Validating Transfer to Queue Title");
        return isEnabled(pageElements.pageTitle);
    }

    /**
     * This method is use to click transfer to queue button based on queue name
     * @param queueName The Queue name
     */
    public void clickTransferQueue(String queueName) {
        By transferQueue = By.xpath( pageElements.option+ queueName + pageElements.transferQueueBtn);
        clickAndWaitForLoaderToBeRemoved(transferQueue);
        commonLib.info("Transferring Ticket to Ticket Pool Name: " + queueName);
    }

    /**
     * This method is use to click close tab
     */
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
