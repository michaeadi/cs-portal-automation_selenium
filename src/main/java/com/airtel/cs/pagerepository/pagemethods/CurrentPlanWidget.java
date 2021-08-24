package com.airtel.cs.pagerepository.pagemethods;

import com.airtel.cs.pagerepository.pageelements.CurrentPlanWidgetPage;
import com.airtel.cs.pagerepository.pageelements.PlanAndPackDetailedWidgetPage;
import lombok.extern.log4j.Log4j2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Log4j2
public class CurrentPlanWidget extends BasePage {

    CurrentPlanWidgetPage pageElements;
    private final String SCROLL_TO_WIDGET_MESSAGE=config.getProperty("scrollToWidgetMessage");
    List<WebElement> as;

    public CurrentPlanWidget(WebDriver driver) {
        super(driver);
        pageElements = PageFactory.initElements(driver, CurrentPlanWidgetPage.class);
    }

    /**
     * This method use to check whether current plan widget display or not
     *
     * @return true/false
     */
    public Boolean isCurrentPlanWidgetDisplay() {
        commonLib.info("Checking that Current Plan widget is Display");
        Boolean status = false;
        try {
            scrollToViewElement(pageElements.getTitle);
            status=isElementVisible(pageElements.getTitle);
        } catch (InterruptedException e) {
            e.printStackTrace();
            commonLib.fail(SCROLL_TO_WIDGET_MESSAGE,true);
        }
        return status;
    }

    /*
       This Method will give us footer auuid shown in Current plan
       Current plan
        */
    public String getFooterAuuid() {
        commonLib.info(getText(pageElements.footerUHWAuuid));
        return getText(pageElements.footerUHWAuuid);
    }

    /*
    This Method will give us auuid shown in the middle of the Current plan
     */
    public String getMiddleAuuid() {
        String result;
        result = getAttribute(pageElements.middleUHWAuuid, "data-auuid", false);
        commonLib.info(result);
        return result;
    }

    /**
     * This method use validate plan name on current plan widget
     *
     * @return true/false
     */
    public boolean isPlanNameDisplayedOnCurrentPlanWidget() {
        commonLib.info("Checking is name of the plan displayed on Current Plan Widget");
        commonLib.info(getText(pageElements.planNameOnCurrentPlanWidget));
        return isElementVisible(pageElements.planNameOnCurrentPlanWidget);
    }

    /**
     * This method to validate additional count on current plan widget
     *
     * @return true/false
     */
    public boolean isAdditionalBundleOnCurrentPlanWidget() {
        commonLib.info("Checking is additional bundle visible on Current Plan Widget");
        commonLib.info(getText(pageElements.additionalBundleCountWidget));
        return isElementVisible(pageElements.additionalBundleCountWidget);
    }

    public String isAdditionalBundleCountOnCurrentPlanWidget(){
        commonLib.info("Getting additional bundle count visible on Current Plan Widget");
        return getText(pageElements.additionalBundleCountWidget);
    }

    /**
     * This method is use to get number of data rows display on UI
     *
     * @return Integer the count
     */
    public int getNumberOfRows() {
        return as.size();
    }

    /**
     * This method will return boolean value true if found decimal value upto 2
     *
     * @param input_string
     * @return
     */
    public static boolean getDecimalValue(String input_string) throws ArrayIndexOutOfBoundsException {

        boolean flag = false;
        if (!input_string.equalsIgnoreCase("-")) {
            String[] result = input_string.split("\\.");
            if (result[1].length() == 2) {
                flag = true;
            }
        }
        return flag;
    }

}