package pages;

import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class ViewTicketPagePOM extends BasePage {

    By ticketIdValue = By.xpath("//*[@id=\"style-3\"]/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-supervisor-ticket-details/mat-sidenav-container/mat-sidenav-content/div[2]/div/div/table/tbody/tr[1]/td[1]/span[2]");
    By selectState = By.xpath("//body//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div//div[2]");
    By submitAs = By.className("submit-btn");
    By submitBtn = By.xpath("//*[@id=\"cdk-accordion-child-0\"]/div/div/div/div[2]/div[2]/div/form[1]/button/span/span[2]");
    By stateName = By.xpath("/html/body/app-root/app-dashboard/div[2]/app-admin-panel/div/div/app-sidenav-bar/mat-sidenav-container/mat-sidenav-content/div/app-service-request/div/app-supervisor-ticket-details/mat-sidenav-container/mat-sidenav-content/div[2]/div/mat-accordion[2]/mat-expansion-panel/div/div/div/div/div[2]/div[2]/div/form[1]/button/span/span[2]");

    public ViewTicketPagePOM(WebDriver driver) {
        super(driver);
    }

    public String getTicketId() {
        log.info("View Ticket: " + readText(ticketIdValue));
        return readText(ticketIdValue);
    }

    public String selectState(String state) throws InterruptedException {
        log.info("Selecting State: " + state);
        //By stateName= By.xpath("//div[@class=\"ng-tns-c9-325 ng-trigger ng-trigger-transformPanel mat-select-panel mat-primary\"]//span[contains(text(),' "+state+"')]");
        //By stateName=By.xpath("//span[contains(text(),' "+state+"')]");
        scrollToViewElement(submitAs);
        String selectedState = readText(stateName);
        //click(selectState);
        click(submitAs);
        return selectedState;
    }

    public String getStatename() {
        log.info("State: " + readText(stateName));
        return readText(stateName);
    }
}
