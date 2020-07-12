package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class transferToQueuePOM extends BasePage {

    By pageTitle= By.xpath("//*[@id=\"assignQueue\"]/app-assign-to-queue/section/div/div[1]/h4");
    By transferQueue=By.xpath("//*[@id=\"assignQueue\"]/app-assign-to-queue/section/div/div[2]/div[1]/div[2]/img");

    public transferToQueuePOM(WebDriver driver) {
        super(driver);
    }

    public boolean validatePageTitle(){
        log.info("Validating Transfer to Queue Title");
        return checkState(pageTitle);
    }

    public void clickTransferQueue(){
        log.info("Clicking on Transfer to Button");
        click(transferQueue);
    }
}
