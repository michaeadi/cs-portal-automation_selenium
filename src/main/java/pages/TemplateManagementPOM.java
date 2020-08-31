package pages;

import Utils.ExtentReports.ExtentTestManager;
import com.relevantcodes.extentreports.LogStatus;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j2
public class TemplateManagementPOM extends BasePage{

    By createdTemplateTab=By.xpath("//div[@role=\"tab\"][1]");
    By viewCreatedTemplateTab=By.xpath("//div[@role=\"tab\"][2]");

    public TemplateManagementPOM(WebDriver driver) {
        super(driver);
    }

    public boolean isPageLoaded() {
        waitVisibility(createdTemplateTab);
        log.info("Checking that is Template Management Page is loaded : " + checkState(createdTemplateTab));
        ExtentTestManager.getTest().log(LogStatus.INFO, "Checking that is Template Management Page is loaded : " + checkState(createdTemplateTab));
        return checkState(createdTemplateTab);
    }

    public void clickCreatedTemplateTab(){
        log.info("Clicking on created template");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on created template");
        click(createdTemplateTab);
    }

    public ViewCreatedTemplatePOM clickViewCreatedTemplateTab(){
        log.info("Clicking on view created template");
        ExtentTestManager.getTest().log(LogStatus.INFO,"Clicking on view created template");
        click(viewCreatedTemplateTab);
        return new ViewCreatedTemplatePOM(driver);
    }
}
