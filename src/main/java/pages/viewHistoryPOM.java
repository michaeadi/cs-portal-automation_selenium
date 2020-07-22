package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class viewHistoryPOM extends BasePage {
    By firstIssueCode = By.xpath("//tbody/tr[1]/td[7]/p");
    By interactionsTab = By.xpath("//div[@class=\"mat-tab-label-content\" and contains(text(),\"Interaction\")]");

    public viewHistoryPOM(WebDriver driver) {
        super(driver);
    }

    public void clickOnInteractionsTab() {
        log.info("Clicking on Interactions Tab under view history ");
        waitTillLoaderGetsRemoved();
        click(interactionsTab);
    }

    public String getLastCreatedIssueCode() {
        log.info("Getting the issue code of last created FTR interaction ");
        waitTillLoaderGetsRemoved();
        return readText(firstIssueCode);
    }


}
