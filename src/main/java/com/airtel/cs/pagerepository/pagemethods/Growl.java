package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.GrowlPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
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
    This Method will check is growl visible or not after login
     */
    public boolean checkIsGrowlVisible() {
        return validateGrowlVisible(growlPage.growl);
    }

    /**
     * This method is use to get growl message based on element location
     * @param elementLocation The element location
     * @return true/false
     */
    public boolean validateGrowlVisible(By elementLocation) {
        boolean result = false;
        try {
            result = elementVisibleWithExplictWait(elementLocation);
        } catch (Exception e) {
            log.error("Growl is not visible", e.getCause());
        }
        return result;
    }

    /**
     * This method use to check growl message display or not after putting msisdn
     * @return true/false
     */
    public boolean checkIsDashboardGrowlVisible() {
        return validateGrowlVisible(growlPage.dashboardGrowl);
    }


    /*
    This Method will given toast content after login
     */
    public String getToastContent() {
        return getText(growlPage.growl);
    }

    /**
     * This method use to get dashboard growl message
     * @return String The message
     */
    public String getDashboardToastMessage(){
        return getText(growlPage.dashboardGrowl);
    }
}
