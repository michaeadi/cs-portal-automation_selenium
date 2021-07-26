package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.GrowlPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

@Log4j2
public class Growl extends BasePage {

    public GrowlPage growlPage;

    // initialize elements
    public Growl(WebDriver driver) {
        super(driver);
        growlPage = PageFactory.initElements(driver, GrowlPage.class);
    }

    /*
    This Method will check is growl visibleor not
     */
    public boolean checkIsGrowlVisible() {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(growlPage.growl);
        } catch (Exception e) {
            log.error("Growl is not visible", e.getCause());
        }
        return result;
    }

    /*
    This Method will given toast content
     */
    public String getToastContent() {
        return getText(growlPage.growl);
    }
}
